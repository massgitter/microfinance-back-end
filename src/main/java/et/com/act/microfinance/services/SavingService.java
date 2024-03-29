package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Saving;
import et.com.act.microfinance.repos.SavingRepo;
import et.com.act.microfinance.repos.StatusRepo;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.SavingResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingRepo savingRepo;
    private final StatusService statusService;
    private final FiscalYearService fiscalYearService;
    private final SavingPeriodService savingPeriodService;
    private final EntityManager entityManager;

    public List<SavingResponse> getAll() {
        return savingRepo.findAll()
                .stream()
                .map(Saving::savingResponse)
                .collect(Collectors.toList());
    }

    public PersistenceResponse generate(Long month, Double amount) {
        try {
            Session session = entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("select generate_monthly_saving(?, ?)");
                preparedStatement.setLong(1, month);
                preparedStatement.setDouble(2, amount);
                preparedStatement.execute();
            });
            return new PersistenceResponse(true, "Successfully generated");
        }catch (Exception e) {
            e.printStackTrace();
            return new PersistenceResponse(false, "Failed to Generate");
        }
    }

    public PersistenceResponse create(Customer customer) {
        try {
            Saving build = Saving.builder()
                    .description("Onboarding Fee")
                    .penalty(0.0)
                    .status(statusService.findById(4L))
                    .amountDuty(0.0)
                    .totalAmount(0.0)
                    .overDuty(0.0)
                    .customer(customer)
                    .savingPeriod(savingPeriodService.findByMonth("DEFAULT"))
                    .build();
            savingRepo.save(build);
            return new PersistenceResponse(true, "success");
        }catch (Exception e) {
            return new PersistenceResponse(false, e.getMessage());
        }
    }
}
