package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Profession;
import et.com.act.microfinance.repos.ProfessionRepo;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.ProfessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessionService {
    private final ProfessionRepo professionRepo;

    public PersistenceResponse create(String description) {
        try {
            Profession byDescription = findByDescription(description);
            if (Objects.isNull(byDescription)) {
                Profession profession = Profession.builder()
                        .description(description)
                        .build();
                professionRepo.save(profession);
            }

            return new PersistenceResponse(true, "Sucess!");
        }catch (Exception e) {
            return new PersistenceResponse(false, e.getMessage());

        }
    }

    public Profession findByDescription(String description) {
        return professionRepo.findByDescription(description);
    }

    public List<ProfessionResponse> getAll() {
        return professionRepo.findAll()
                .stream()
                .map(Profession::professionResponse)
                .collect(Collectors.toList());
    }

    public ProfessionResponse findById(Long id) {
        return Objects.requireNonNull(professionRepo.findById(id).orElse(null)).professionResponse();
    }
}
