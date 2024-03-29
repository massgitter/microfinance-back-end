package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.SavingResponse;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Optional;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Saving extends Shared {
    private String description;
    private String referenceNumber;
    private String merchantId;
    private Double amountDuty;
    private Double overDuty;
    private Double totalAmount;
    private Double penalty;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "month")
    private SavingPeriod savingPeriod;

    public SavingResponse savingResponse() {
        return SavingResponse.builder()
                .id(getId())
                .description(description)
                .amountDuty(amountDuty)
                .overDuty(overDuty)
                .penalty(penalty)
                .totalAmount(totalAmount)
                .status(status.getName())
                .customerResponse(customer.customerResponse())
                .savingPeriodResponse(Optional.of(savingPeriod.savingPeriodResponse()).orElse(null))
                .build();
    }
}
