package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Penalty;
import et.com.act.microfinance.models.Saving;
import et.com.act.microfinance.models.Status;
import et.com.act.microfinance.repos.CustomerRepo;
import et.com.act.microfinance.repos.SavingRepo;
import et.com.act.microfinance.requests.DoSavingRequest;
import et.com.act.microfinance.requests.SavingRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.SavingResponse;
import et.com.act.microfinance.utils.PenaltyMethodsEnum;
import et.com.act.microfinance.utils.Statuses;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final PenaltyService penaltyService;
    private final CustomerRepo customerRepo;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            return new PersistenceResponse(false, "Something went wrong!");
        }
    }

    public PersistenceResponse monthlySavings(SavingRequest request) {
        Customer customer = customerRepo.findById(request.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found with the given id"));
        Saving saving = Saving.builder()
                .description(savingPeriodService.findById(request.getSavingPeriod()).getMonth())
                .amountDuty(200.0)
                .overDuty(request.getAmount() - 200.0)
                .savingPeriod(savingPeriodService.findById(request.getSavingPeriod()))
                .customer(customer)
                .totalAmount(request.getAmount())
                .status(statusService.findById(4))
                .build();

        Optional<Saving> aDefault = savingRepo.findByCustomer(customer).stream().filter(saving1 -> saving1.getSavingPeriod().getMonth().equals("DEFAULT")).findFirst();
        if (aDefault.isPresent() && aDefault.get().getStatus().getName().equals(Statuses.PAID.getName())) {
            savingRepo.save(saving);
            return new PersistenceResponse(true, "Success");
        } else if (aDefault.isPresent() && aDefault.get().getStatus().getName().equals(Statuses.PENDING.getName())) {
            return new PersistenceResponse(false, "you must pay boarding fee first");
        }


        return new PersistenceResponse(false, "Something went wrong");

    }

    public List<Saving> findLastSixMonthsPerCustomer(Customer customer) {
        return savingRepo.findByCustomer(customer);
    }

    public List<Saving> findByCustomerId(String customerId) {
        Customer customer = customerRepo.findByIdCard(customerId);
        return savingRepo.findByCustomer(customer);
    }

    public PersistenceResponse doSaving(DoSavingRequest request) {
        Customer customer = customerRepo.findByIdCard(request.getCustomerId());
        Saving saving = savingRepo.findByCustomer(customer)
                .stream()
                .filter(s -> s.getStatus().getName().equals("PENDING"))
                .collect(Collectors.toList())
                .get(0);

        if (Objects.nonNull(saving)){
            Status status = statusService.findById(5);
            if (saving.getSavingPeriod().getMonth().equals("DEFAULT")) {
                if (request.getAmount() != 1200) {
                    return new PersistenceResponse(false, "Amount must be 1200");
                }else {
                    saving.setStatus(status);
                    savingRepo.save(saving);
                    customer.setStatus(statusService.findById(1));
                    customerRepo.save(customer);
                    return new PersistenceResponse(true, "Success!");
                }
            }

            if (Objects.equals(request.getAmount(), saving.getAmountDuty())) {
                saving.setStatus(status);
                savingRepo.save(saving);
                customer.setStatus(statusService.findById(1));
                customerRepo.save(customer);
                return new PersistenceResponse(true, "Success!");
            }else if (request.getAmount() > saving.getAmountDuty()) {
                saving.setStatus(status);
                saving.setOverDuty(request.getAmount() - saving.getAmountDuty());
                saving.setTotalAmount(request.getAmount());
                savingRepo.save(saving);
                customer.setStatus(statusService.findById(1));
                customerRepo.save(customer);
                return new PersistenceResponse(true, "Success!");
            }else {
                return new PersistenceResponse(false, "The given amount must be greater that 200");
            }
        }
        return new PersistenceResponse(false, "Something went wrong!");
    }


//    @Scheduled(cron = "0 0 0 * * *") // Runs every midnight
//    @Scheduled(cron = "0 * * * * *") // Runs every minute
    public void calculatePenalty() {
        List<Saving> savings = savingRepo.findAll()
                .stream()
                .filter(saving -> !saving.getSavingPeriod().getMonth().equals("DEFAULT"))
                .filter(saving -> saving.getSavingPeriod().getEndsAt().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        Penalty penalty = penaltyService.getAll().get(0);
        if (penalty.getPenaltyMethods().getName().equals(PenaltyMethodsEnum.FIXED.getName())) {
            savings.forEach(saving -> {
                saving.setPenalty(penalty.getAmount());
                savingRepo.save(saving);
                System.out.println("Penalty calculated for ".concat(saving.getCustomer().getFirstName().concat(" ").concat(saving.getCustomer()
                        .getMiddleName())).concat(" for ").concat(saving.getSavingPeriod().getMonth()));
            });

            System.out.println("Total number of customers =======================> ".concat(String.valueOf(savings.size())));
        }
    }


}
