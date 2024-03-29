package et.com.act.microfinance.repos;

import et.com.act.microfinance.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepo extends JpaRepository<Sector, Long> {
    Sector findByName(String name);
}
