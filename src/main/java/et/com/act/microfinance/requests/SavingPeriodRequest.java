package et.com.act.microfinance.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingPeriodRequest {
    private String month;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private Long fiscalYear;
}
