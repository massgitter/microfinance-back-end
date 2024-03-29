package et.com.act.microfinance.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingResponse {
    private Long id;
    private String description;
    private Double amountDuty;
    private Double overDuty;
    private Double penalty;
    private Double totalAmount;
    private String status;
    private CustomerResponse customerResponse;
    private SavingPeriodResponse savingPeriodResponse;
}
