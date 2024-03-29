package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.RegistrationFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationFeeRepo extends JpaRepository<RegistrationFee, Long> {
}
