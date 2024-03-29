package et.com.act.microfinance.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Date issuedAt;
    private Date returnDate;
    private Double interest;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;
}
