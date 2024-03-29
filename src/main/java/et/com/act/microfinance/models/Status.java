package et.com.act.microfinance.models;
import lombok.*;

import javax.persistence.Entity;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Status extends Shared {
    private String name;
}
