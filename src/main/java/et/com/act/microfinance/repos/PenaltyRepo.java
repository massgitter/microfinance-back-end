package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepo extends JpaRepository<Penalty, Long> {
}
