package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepo  extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomer(Customer customer);
}
