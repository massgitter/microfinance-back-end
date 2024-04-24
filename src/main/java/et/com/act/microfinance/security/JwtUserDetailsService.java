package et.com.act.microfinance.security;

import et.com.act.microfinance.utils.RolesEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.TextType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersRepo usersRepo;
    private final PasswordEncoder bcryptEncoder;
    private final RoleRepo roleRepo;
    private final JwtEncoder jwtEncoder;


    //Act Default Username and password
    @Value("${act.email}")
    private String actUser;

    @Value("${act.fullName}")
    private String actFullName;

    @Value("${act.password}")
    private String actPassword;

    //jwt default expiration and issuer
    @Value("${microfinance.app.jwtExpirationSec}")
    private Long expiry;

    @Value("${microfinance.app.issuer}")
    private String issuer;

    @Transactional
    public UsersResponse create(CreateUserRequest request) {
        if (Objects.nonNull(usersRepo.findByUsername(request.getUsername()))) {
            throw new ValidationException("Username exists!");
        }
        Users user = new Users();
        //Check the if the Party Id exists and check for each party

        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(bcryptEncoder.encode(request.getPassword()));
        user.setRole(
                request.getRole() == null ? null : roleRepo.findByName(request.getRole().getName())
        );

        user = usersRepo.save(user);

        return user.usersResponse();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepo.findByUsername(username);
    }


    public void onInit() {
        Users users = this.usersRepo.findByUsername(this.actUser);

        if (Objects.isNull(users)) {
            String password = bcryptEncoder.encode(this.actPassword);
            Role role = roleRepo.findByName(RolesEnum.ADMIN.getName());
            Users user = new Users();
            user.setUsername(this.actUser);
            user.setFullName(this.actFullName);
            user.setEnabled(true);
            user.setPassword(password);
            user.setRole(role);
            usersRepo.save(user);
        }
    }

    public String generateToken(Users user, Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds((long) expiry))
                .subject(String.format("%s,%s", user.getId(), user.getUsername()))
                .claim("roles", scope)
                .build();

        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return token;
    }

    public UsersResponse get(long id) {
        return usersRepo.getById(id).usersResponse();
    }

    @Transactional
    public UsersResponse update(long id, UpdateUserRequest request) {
        Optional<Users> users = usersRepo.findById(id);
        if (users.isPresent()) {
            Users user = users.get();
            user.setFullName(request.getFullName());
            user.setRole(
                    request.getRole() == null ? null : roleRepo.findByName(request.getRole().getName())
            );
            return usersRepo.save(user).usersResponse();
        }

        return null;

    }

    @Transactional
    public UsersResponse delete(long id) {
        Optional<Users> users = usersRepo.findById(id);
        if (users.isPresent()) {
            Users user = users.get();
            user.setUsername(user.getUsername().replace("@", String.format("_%s@", user.getId().toString())));
            user.setEnabled(false);
            return usersRepo.save(user).usersResponse();
        }

        return null;
    }

    public ResponseEntity<Map<String, Object>> search(SearchUsersRequest dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> userPage = usersRepo.searchUsers(dto.getId(), dto.getUserName(), new TypedParameterValue(new TextType(), dto.getFullName()), pageable);
        Map<String, Object> response = new HashMap<>();
        List<UsersResponse> collect = userPage.stream().collect(Collectors.toList()).stream().map(Users::usersResponse).collect(Collectors.toList());
        response.put("users", collect);
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
