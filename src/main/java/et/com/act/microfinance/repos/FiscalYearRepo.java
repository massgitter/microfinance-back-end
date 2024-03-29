package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.FiscalYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiscalYearRepo extends JpaRepository<FiscalYear, Long> {
    FiscalYear findByYear(int year);

}
