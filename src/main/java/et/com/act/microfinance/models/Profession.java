package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.ProfessionResponse;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Profession extends Shared {
    private String description;

    public ProfessionResponse professionResponse() {
        return ProfessionResponse.builder()
                .id(getId())
                .description(description)
                .build();
    }
}
