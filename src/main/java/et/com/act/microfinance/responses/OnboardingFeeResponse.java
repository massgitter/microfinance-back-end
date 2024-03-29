package et.com.act.microfinance.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingFeeResponse {
    private Long id;
    private String description;
    private Double amount;

}
