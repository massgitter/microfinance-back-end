package et.com.act.microfinance.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiscalYearRequest {
    private int year;
    private String description;
    private String startingMonth;
    private int paymentStartsAt;
    private int paymentActiveFor;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
}
