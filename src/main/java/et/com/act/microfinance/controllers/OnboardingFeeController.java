package et.com.act.microfinance.controllers;

import et.com.act.microfinance.requests.OnBoardingFeeRequest;
import et.com.act.microfinance.responses.OnboardingFeeResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.services.OnBoardingFeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onboardingFee")
@RequiredArgsConstructor
@Tag(name = "OnBoarding Fee")
@CrossOrigin("http://localhost:4200/")
public class OnboardingFeeController {
    private final OnBoardingFeeService onBoardingFeeService;

    @PostMapping("/create")
    public PersistenceResponse create(@RequestBody OnBoardingFeeRequest request) {
        return onBoardingFeeService.create(request);
    }

    @PutMapping("/edit")
    public PersistenceResponse update(@RequestBody OnBoardingFeeRequest request) {
        return onBoardingFeeService.update(request);
    }

    @GetMapping("/byId/{id}")
    public OnboardingFeeResponse byId(@PathVariable Long id) {
        return onBoardingFeeService.byId(id);
    }

    @GetMapping("/all")
    public List<OnboardingFeeResponse> getAll() {
        return onBoardingFeeService.getAll();
    }

}
