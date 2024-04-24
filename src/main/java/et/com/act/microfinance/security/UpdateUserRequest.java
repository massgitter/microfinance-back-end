package et.com.act.microfinance.security;

import et.com.act.microfinance.utils.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @NotBlank
    private String fullName;
    private RolesEnum role;
    private long partyID;
}
