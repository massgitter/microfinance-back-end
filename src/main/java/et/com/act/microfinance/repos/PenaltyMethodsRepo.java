package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.PenaltyMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyMethodsRepo extends JpaRepository<PenaltyMethods, Long> {
    PenaltyMethods findByName(String name);
}
