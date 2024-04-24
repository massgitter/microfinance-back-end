package et.com.act.microfinance.requests;

import et.com.act.microfinance.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private Long customerId;
    private double amount;
}
