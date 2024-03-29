package et.com.act.microfinance.services;

import et.com.act.microfinance.models.FiscalYear;
import et.com.act.microfinance.models.SavingPeriod;
import et.com.act.microfinance.repos.FiscalYearRepo;
import et.com.act.microfinance.repos.SavingPeriodRepo;
import et.com.act.microfinance.requests.FiscalYearRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiscalYearService {
    private final FiscalYearRepo fiscalYearRepo;
    private final SavingPeriodRepo savingPeriodRepo;

    public PersistenceResponse create(FiscalYearRequest request) {
        try {
            if (request.getEndsAt().compareTo(request.getStartsAt()) > 0) {
                if (Objects.isNull(fiscalYearRepo.findByYear(request.getYear()))) {
                    FiscalYear fiscalYear = FiscalYear.builder()
                            .year(request.getYear())
                            .description(request.getDescription())
                            .startsAt(request.getStartsAt())
                            .endsAt(request.getEndsAt())
                            .build();
                    if (Objects.isNull(request.getDescription())) {
                        fiscalYear.setDescription(String.valueOf(request.getYear()).concat(" ").concat("Fiscal Year"));
                    }
                    fiscalYearRepo.save(fiscalYear);
                } else {
                    return new PersistenceResponse(false, "Already Exists");
                }
            }else {
                return new PersistenceResponse(false, "The End Date of a Fiscal year must be after its start date");
            }

            FiscalYear fiscalYear = fiscalYearRepo.findByYear(request.getYear());
            int year = request.getYear();

            SavingPeriod DEFAULT = SavingPeriod.builder()
                    .month("DEFAULT")
                    .startsAt(LocalDate.now())
                    .endsAt(LocalDate.now().plusDays(5))
                    .fiscalYear(fiscalYear)
                    .build();
            if (Objects.isNull(savingPeriodRepo.findByMonthAndFiscalYear("DEFAULT", fiscalYear))) {
                savingPeriodRepo.save(DEFAULT);
            }

            List<String> months = Arrays.asList("SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST");

            List<SavingPeriod> savingPeriods = months.stream()
                    .map(month -> {
                        LocalDate startsAt = LocalDate.of(year, Month.valueOf(month), request.getPaymentStartsAt());
                        LocalDate endsAt = LocalDate.of(year, Month.valueOf(month), request.getPaymentActiveFor());
                        return SavingPeriod.builder()
                                .month(month)
                                .startsAt(startsAt)
                                .endsAt(endsAt)
                                .fiscalYear(fiscalYear)
                                .build();
                    })
                    .collect(Collectors.toList());

            SavingPeriod period = savingPeriods.stream()
                    .filter(savingPeriod -> savingPeriod.getMonth().equals(request.getStartingMonth()))
                    .findFirst()
                    .orElse(null);

            if (period != null) {
                for (SavingPeriod savingPeriod : savingPeriods) {
                    if (Objects.isNull(savingPeriodRepo.findByMonthAndFiscalYear(savingPeriod.getMonth(), fiscalYear)) &&
                            savingPeriods.indexOf(savingPeriod) >= savingPeriods.indexOf(period)) {
                        if (savingPeriod.getEndsAt().isAfter(savingPeriod.getStartsAt())) {
                            savingPeriodRepo.save(savingPeriod);
                        }else {
                            return new PersistenceResponse(false, "The End date of a Saving period must be after its starting date");
                        }
                    }
                }
            }

            return new PersistenceResponse(true, "Success");
        } catch (Exception e) {
            return new PersistenceResponse(false, "Failed to Register");
        }
    }


    public FiscalYear findById(Long fiscalYear) {
        return Objects.requireNonNull(fiscalYearRepo.findById(fiscalYear).orElse(null));
    }

    public List<FiscalYear> getAll() {
        return fiscalYearRepo.findAll();
    }
}
