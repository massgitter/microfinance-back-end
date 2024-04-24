package et.com.act.microfinance.controllers;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Saving;
import et.com.act.microfinance.repos.CustomerRepo;
import et.com.act.microfinance.requests.DoSavingRequest;
import et.com.act.microfinance.requests.SavingRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.SavingResponse;
import et.com.act.microfinance.services.CustomerService;
import et.com.act.microfinance.services.SavingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/savings")
@RequiredArgsConstructor
@Tag(name = "Savings")
@CrossOrigin("http://localhost:4200/")
public class SavingController {
    private final SavingService savingService;
    private final CustomerRepo customerRepo;

    @PostMapping("/generate")
    public PersistenceResponse generate(Long month, Double amount) {
        return savingService.generate(month, amount);
    }

    @GetMapping("/all")
    public List<SavingResponse> getAll() {
        return savingService.getAll();
    }

    @GetMapping("/customerId/{customerId}")
    public List<SavingResponse> byCustomerId(@PathVariable("customerId") String customerId) {
        return savingService.findByCustomerId(customerId)
                .stream()
                .map(Saving::savingResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("monthly")
    public PersistenceResponse monthlySavings(@RequestBody SavingRequest request) {
        return savingService.monthlySavings(request);
    }

    @PostMapping("/doSaving/{customerId}")
    public PersistenceResponse doSaving(@RequestBody DoSavingRequest request) {
        return savingService.doSaving(request);
    }
}
