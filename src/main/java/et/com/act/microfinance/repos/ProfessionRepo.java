package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepo extends JpaRepository<Profession, Long> {
    Profession findByDescription(String workStatus);
}
