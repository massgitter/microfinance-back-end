package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Penalty;
import et.com.act.microfinance.models.PenaltyMethods;
import et.com.act.microfinance.repos.PenaltyMethodsRepo;
import et.com.act.microfinance.repos.PenaltyRepo;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.utils.PenaltyMethodsEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepo penaltyRepo;
    private final PenaltyMethodsRepo penaltyMethodsRepo;

    public void createMethods(String name, String description) {
        if (Objects.isNull(penaltyMethodsRepo.findByName(name))) {
            PenaltyMethods penaltyMethods = PenaltyMethods.builder()
                    .name(name)
                    .description(description)
                    .build();
            penaltyMethodsRepo.save(penaltyMethods);
        }
    }

    public void onInit() {
        Arrays.stream(PenaltyMethodsEnum.values())
                .forEach(pm -> createMethods(pm.getName(), pm.getDescription()));
    }

    public PersistenceResponse create(Double amount, Long penaltyMethod) {
        if (penaltyRepo.findAll().isEmpty()) {
            Optional<PenaltyMethods> penaltyMethods = penaltyMethodsRepo.findById(penaltyMethod);

            if (penaltyMethods.isPresent()) {
                Penalty penalty = Penalty.builder()
                        .amount(amount)
                        .penaltyMethods(penaltyMethods.get())
                        .build();
                penaltyRepo.save(penalty);

                return new PersistenceResponse(true, "Success!");
            }
        }

        return new PersistenceResponse(false, "Failed to create!");
    }

    public List<Penalty> getAll() {
        return penaltyRepo.findAll();
    }
}
