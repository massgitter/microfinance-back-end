package et.com.act.microfinance.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingRequest {
    private Long customerId;
    private Long SavingPeriod;
    private Double amount;
}
