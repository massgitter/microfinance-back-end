package et.com.act.microfinance.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    private Long id;
    private CustomerResponse customerResponse;
    private double amount;
    private double interest;
    private double repaid;
    private double remaining;
    private LocalDateTime issuedAt;
    private LocalDateTime returnDAte;
}
