package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.OnboardingFeeResponse;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OnboardingFee extends Shared {
    private String description;
    private Double amount;

    public OnboardingFeeResponse onboardingFeeResponse() {
        return OnboardingFeeResponse.builder()
                .id(getId())
                .description(description)
                .amount(amount)
                .build();
    }
}
