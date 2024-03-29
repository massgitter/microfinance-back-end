package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.FiscalYearResponse;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FiscalYear extends Shared {
    @Column(nullable = false)
    private int year;

    private String description;

    @Column(nullable = false)
    private Date startsAt;

    @Column(nullable = false)
    private Date endsAt;

    public FiscalYearResponse fiscalYearResponse() {
        return FiscalYearResponse.builder()
                .id(getId())
                .year(year)
                .description(description)
                .build();
    }
}
