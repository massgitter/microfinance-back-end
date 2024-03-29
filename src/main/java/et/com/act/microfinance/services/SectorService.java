package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Sector;
import et.com.act.microfinance.repos.SectorRepo;
import et.com.act.microfinance.utils.SectorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepo sectorRepo;

    public void create(String name) {
        if (Objects.isNull(sectorRepo.findByName(name))) {
            Sector sector = Sector.builder()
                    .name(name)
                    .build();
            sectorRepo.save(sector);
        }
    }


    public void onInit() {
        Arrays.stream(SectorEnum.values())
                .forEach(sectorEnum -> create(sectorEnum.name()));
    }
}
