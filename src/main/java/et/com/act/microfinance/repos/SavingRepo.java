package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepo extends JpaRepository<Saving, Long> {
}
