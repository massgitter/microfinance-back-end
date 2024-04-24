package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingRepo extends JpaRepository<Saving, Long> {

    List<Saving> findByCustomer(Customer customer);

    List<Saving> findByCustomer_IdCard(String customer_idCard);

}
