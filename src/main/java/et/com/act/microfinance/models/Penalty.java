package et.com.act.microfinance.models;

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
public class Penalty extends Shared {
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "method")
    private PenaltyMethods penaltyMethods;
}
