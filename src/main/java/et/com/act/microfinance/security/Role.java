package et.com.act.microfinance.security;

import et.com.act.microfinance.models.Shared;
import et.com.act.microfinance.utils.RolesEnum;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends Shared {

    @Column(unique = true)
    private String name;
}
