package et.com.act.microfinance.controllers;

import et.com.act.microfinance.models.SavingPeriod;
import et.com.act.microfinance.requests.SavingPeriodRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.SavingPeriodResponse;
import et.com.act.microfinance.services.SavingPeriodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savingPeriod")
@RequiredArgsConstructor
@Tag(name = "Saving Period")
@CrossOrigin("http://localhost:4200/")
public class SavingPeriodController {
    private final SavingPeriodService savingPeriodService;

    @PostMapping("/create")
    public PersistenceResponse create(@RequestBody SavingPeriodRequest request) {
        return savingPeriodService.create(request);
    }

    @PutMapping("/update")
    public PersistenceResponse update(@RequestBody SavingPeriodRequest request) {
        return savingPeriodService.update(request);
    }

    @GetMapping("/getAll")
    public List<SavingPeriodResponse> getAll() {
        return savingPeriodService.getAll();
    }

    @GetMapping("/byMonth/{month}")
    public SavingPeriod findByMonth(@PathVariable String month) {
        return savingPeriodService.findByMonth(month);
    }

    @GetMapping("/byId/{id}")
    public SavingPeriodResponse findById(@PathVariable("id") Long id) {
        return savingPeriodService.findById(id);
    }
}
