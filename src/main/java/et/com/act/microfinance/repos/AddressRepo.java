package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Address findAddressByCityAndWoredaAndKebeleAndPhone(String city, String woreda, String kebele, String phone);
}
