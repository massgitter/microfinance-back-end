package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.SavingPeriodResponse;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SavingPeriod extends Shared {
    private String month;
    private LocalDate startsAt;
    private LocalDate endsAt;

    @ManyToOne
    @JoinColumn(name = "fiscalYear")
    private FiscalYear fiscalYear;

    public SavingPeriodResponse savingPeriodResponse() {
        return SavingPeriodResponse.builder()
                .id(getId())
                .month(month)
                .startsAt(startsAt)
                .endsAt(endsAt)
                .fiscalYearResponse(fiscalYear.fiscalYearResponse())
                .build();
    }
}
