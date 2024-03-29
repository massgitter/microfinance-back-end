package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Loan;
import et.com.act.microfinance.models.OnboardingFee;
import et.com.act.microfinance.repos.OnboardingFeeRepo;
import et.com.act.microfinance.requests.OnBoardingFeeRequest;
import et.com.act.microfinance.responses.OnboardingFeeResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.utils.onBoardingFees;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnBoardingFeeService {
    private final OnboardingFeeRepo onboardingFeeRepo;


    public PersistenceResponse create(OnBoardingFeeRequest request) {
        try {
            OnboardingFee byDescription = onboardingFeeRepo.findByDescription(request.getDescription());
            if (byDescription == null) {
                OnboardingFee onboardingFee = OnboardingFee.builder()
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .build();
                onboardingFeeRepo.save(onboardingFee);
            }

            return new PersistenceResponse(true, "Sucess!");
        }catch (Exception e) {
            return new PersistenceResponse(false, "Failed to Register");
        }
    }


    public List<OnboardingFeeResponse> getAll() {
        return onboardingFeeRepo.findAll()
                .stream()
                .map(OnboardingFee::onboardingFeeResponse)
                .collect(Collectors.toList());
    }

    public OnboardingFeeResponse byId(Long id) {
        return Objects.requireNonNull(onboardingFeeRepo.findById(id).orElse(null)).onboardingFeeResponse();
    }

    public void onInit() {
        Arrays.stream(onBoardingFees.values())
                .forEach(onBoardingFees -> {
                    OnBoardingFeeRequest request = new OnBoardingFeeRequest();
                    request.setDescription(onBoardingFees.getDescription());
                    request.setAmount(onBoardingFees.getValue());
                    create(request);
                });
    }

    public PersistenceResponse update(OnBoardingFeeRequest request) {
        OnboardingFee onboardingFee = onboardingFeeRepo.findById(request.getId()).orElse(null);
        if (Objects.nonNull(onboardingFee)) {
            onboardingFee.setDescription(request.getDescription());
            onboardingFee.setAmount(request.getAmount());
            onboardingFeeRepo.save(onboardingFee);

            return new PersistenceResponse(true, "Update Success");
        }else {
            return new PersistenceResponse(false, "Failed to Update");
        }
    }

    public OnboardingFee findByDescription(String description) {
        return onboardingFeeRepo.findByDescription(description);
    }
}
