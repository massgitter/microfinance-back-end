package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Loan;
import et.com.act.microfinance.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Status, Loan> {
    Status findByName(String name);

    Status findById(Long id);
}
