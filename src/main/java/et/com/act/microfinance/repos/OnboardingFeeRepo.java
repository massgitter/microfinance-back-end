package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.OnboardingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingFeeRepo extends JpaRepository<OnboardingFee, Long> {
    OnboardingFee findByDescription(String description);
}
