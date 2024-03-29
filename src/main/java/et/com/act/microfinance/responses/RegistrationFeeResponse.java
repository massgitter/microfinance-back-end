package et.com.act.microfinance.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFeeResponse {
    private Long id;
    private CustomerResponse customerResponse;
    private Double amount;
    private String status;
}
