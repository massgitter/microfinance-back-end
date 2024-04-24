package et.com.act.microfinance.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String token;
    private String refreshToken;
    private String userName;
    private String fullName;
    private String role;
}
