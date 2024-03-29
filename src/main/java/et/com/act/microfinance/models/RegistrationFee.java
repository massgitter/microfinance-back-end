package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.RegistrationFeeResponse;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegistrationFee extends Shared {
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    public RegistrationFeeResponse registrationFeeResponse() {
        return RegistrationFeeResponse.builder()
                .id(getId())
                .customerResponse(customer.customerResponse())
                .amount(amount)
                .status(status.getName())
                .build();
    }
}
