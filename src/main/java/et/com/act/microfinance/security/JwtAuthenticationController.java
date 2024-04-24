package et.com.act.microfinance.security;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
@SecurityRequirement(name = "pension")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;


    @PostMapping(value ="/register")
    public ResponseEntity<UsersResponse> registerUser(@RequestBody @Valid CreateUserRequest dto) throws Exception{
        return ResponseEntity.ok(userDetailsService.create(dto));
    }
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthRequest request) throws Exception{
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            Users user = (Users) authentication.getPrincipal();
            String token= this.userDetailsService.generateToken(user,authentication);

            LoginResponse response=user.loginResponse();
            response.setToken(token);
            response.setRefreshToken(refreshTokenService.createRefreshToken(user.getId()).getToken());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request, Authentication authentication) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = this.userDetailsService.generateToken(user,authentication);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
    @GetMapping("/get")
    public UsersResponse get(long id){
        return userDetailsService.get(id);
    }
    @PutMapping("/update")
    public UsersResponse update(@RequestBody @Valid UpdateUserRequest updateUserRequest, long id){
        return userDetailsService.update(id,updateUserRequest);
    }

    @DeleteMapping("delete")
    public UsersResponse delete(long id){
        return userDetailsService.delete(id);
    }

    @PostMapping("searchUsers")
    public ResponseEntity<Map<String,Object>> search(@RequestBody SearchUsersRequest dto,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return userDetailsService.search(dto,page,size);
    }
}
