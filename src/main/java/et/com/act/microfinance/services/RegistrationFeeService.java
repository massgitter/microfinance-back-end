package et.com.act.microfinance.services;

import et.com.act.microfinance.models.*;
import et.com.act.microfinance.repos.OnboardingFeeRepo;
import et.com.act.microfinance.repos.RegistrationFeeRepo;
import et.com.act.microfinance.repos.SavingRepo;
import et.com.act.microfinance.repos.StatusRepo;
import et.com.act.microfinance.requests.SavingPeriodRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.RegistrationFeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationFeeService {
    private final StatusService statusService;
 private final RegistrationFeeRepo registrationFeeRepo;
 private final OnBoardingFeeService onBoardingFeeService;

    public PersistenceResponse create(Customer customer) {
        try {
            OnboardingFee registrationFee = onBoardingFeeService.findByDescription("Registration Fee");
            OnboardingFee initialDeposit = onBoardingFeeService.findByDescription("Initial Deposit");
            OnboardingFee shareValue = onBoardingFeeService.findByDescription("Share Value");

            RegistrationFee build = RegistrationFee.builder()
                    .status(statusService.findById(4L))
                    .amount(registrationFee.getAmount() + initialDeposit.getAmount() + shareValue.getAmount())
                    .customer(customer)
                    .build();
            registrationFeeRepo.save(build);
            return new PersistenceResponse(true, "Success");
        }catch (Exception e) {
            return new PersistenceResponse(false, "Failed to create");
        }
    }

    public List<RegistrationFeeResponse> getAll(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return registrationFeeRepo.findAll()
                .stream()
                .map(RegistrationFee::registrationFeeResponse)
                .collect(Collectors.toList());
    }
}
