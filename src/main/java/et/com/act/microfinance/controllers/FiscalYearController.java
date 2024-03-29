package et.com.act.microfinance.controllers;


import et.com.act.microfinance.models.FiscalYear;
import et.com.act.microfinance.requests.FiscalYearRequest;
import et.com.act.microfinance.responses.FiscalYearResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.services.FiscalYearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fiscalYear")
@RequiredArgsConstructor
@Tag(name = "Fiscal Year")
@CrossOrigin("http://localhost:4200/")
public class FiscalYearController {
    private final FiscalYearService fiscalYearService;

    @PostMapping("/save")
    public PersistenceResponse create(@RequestBody FiscalYearRequest request) {
        return fiscalYearService.create(request);
    }

    @GetMapping("/byId/{id}")
    public FiscalYearResponse findById(@PathVariable("id") Long id) {
        return fiscalYearService.findById(id).fiscalYearResponse();
    }

    @GetMapping("/all")
    public List<FiscalYearResponse> getAll() {
        return fiscalYearService.getAll()
                .stream()
                .map(FiscalYear::fiscalYearResponse)
                .collect(Collectors.toList());
    }
}
