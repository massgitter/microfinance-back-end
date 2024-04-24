package et.com.act.microfinance.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchUsersRequest {
    private Long id;
    private String userName;
    private String fullName;
}
