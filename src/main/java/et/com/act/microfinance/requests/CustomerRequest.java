package et.com.act.microfinance.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String idCard;
    private AddressRequest aRequest;
    private String profession;
}
