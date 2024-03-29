package et.com.act.microfinance.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingPeriodResponse {
    private Long id;
    private String month;
    private LocalDate startsAt;
    private LocalDate endsAt;
    private FiscalYearResponse fiscalYearResponse;
}
