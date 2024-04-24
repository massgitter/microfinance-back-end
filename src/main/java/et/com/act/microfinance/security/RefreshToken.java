package et.com.act.microfinance.security;

import et.com.act.microfinance.models.Shared;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RefreshToken extends Shared {
    @OneToOne
    private Users user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
