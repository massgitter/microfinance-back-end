package et.com.act.microfinance.services;

import et.com.act.microfinance.models.FiscalYear;
import et.com.act.microfinance.models.Saving;
import et.com.act.microfinance.models.SavingPeriod;
import et.com.act.microfinance.repos.SavingPeriodRepo;
import et.com.act.microfinance.requests.SavingPeriodRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.SavingPeriodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingPeriodService {
    private final SavingPeriodRepo savingPeriodRepo;
    private final FiscalYearService fiscalYearService;


    public PersistenceResponse create(SavingPeriodRequest request) {
        FiscalYear byId = fiscalYearService.findById(request.getFiscalYear());
        SavingPeriod byMonthAndFiscalYear = findByMonthAndFiscalYear(request.getMonth(), byId);
        if (Objects.isNull(byMonthAndFiscalYear)) {
            SavingPeriod savingPeriod = SavingPeriod.builder()
                    .month(request.getMonth())
                    .startsAt(request.getStartsAt())
                    .endsAt(request.getEndsAt())
                    .fiscalYear(byId)
                    .build();
            savingPeriodRepo.save(savingPeriod);
            return new PersistenceResponse(true, "Success on save!");
        }else {
            return new PersistenceResponse(false, "This period already exists in this fiscal year!");
        }
    }

    public PersistenceResponse update(SavingPeriodRequest request) {
        FiscalYear byId = fiscalYearService.findById(request.getFiscalYear());
        SavingPeriod byMonthAndFiscalYear = savingPeriodRepo.findByMonthAndFiscalYear(request.getMonth(), byId);
        byMonthAndFiscalYear.setMonth(request.getMonth());
        byMonthAndFiscalYear.setStartsAt(request.getStartsAt());
        byMonthAndFiscalYear.setEndsAt(request.getEndsAt());
        return new PersistenceResponse(true, "Success on update");

    }

    public List<SavingPeriodResponse> getAll() {
        return savingPeriodRepo.findAll()
                .stream()
                .filter(savingPeriod -> !savingPeriod.getMonth().equals("DEFAULT"))
                .map(SavingPeriod::savingPeriodResponse)
                .sorted(Comparator.comparing(SavingPeriodResponse::getId))
                .collect(Collectors.toList());
    }

    public SavingPeriod findByMonth(String month) {
        return savingPeriodRepo.findByMonth(month);
    }

    public SavingPeriod findById(Long id) {
        return Objects.requireNonNull(savingPeriodRepo.findById(id).orElse(null));
    }

    public void saveAll(List<SavingPeriod> savingPeriods) {
        savingPeriods.forEach(savingPeriod -> {
            if (savingPeriodRepo.findByMonthAndFiscalYear(savingPeriod.getMonth(), savingPeriod.getFiscalYear()) == null) {
                savingPeriodRepo.saveAll(savingPeriods);
            }
        });
    }

    public SavingPeriod findByMonthAndFiscalYear(String month, FiscalYear fiscalYear) {
        return savingPeriodRepo.findByMonthAndFiscalYear(month, fiscalYear);
    }
}
