package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.FiscalYear;
import et.com.act.microfinance.models.SavingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingPeriodRepo extends JpaRepository<SavingPeriod, Long> {
    SavingPeriod findByMonthAndFiscalYear(String month, FiscalYear fiscalYear);

    SavingPeriod findByMonth(String month);
}
