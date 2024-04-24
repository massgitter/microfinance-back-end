package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.LoanResponse;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Loan extends Shared {
    private Double amount;
    private Double repaid;
    private Double remaining;
    private LocalDateTime issuedAt;
    private LocalDateTime returnDate;
    private Double interest;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    public LoanResponse loanResponse() {
        return LoanResponse.builder()
                .id(getId())
                .customerResponse(customer.customerResponse())
                .amount(amount)
                .repaid(repaid)
                .remaining(remaining)
                .interest(interest)
                .issuedAt(issuedAt)
                .returnDAte(returnDate)
                .build();
    }
}
