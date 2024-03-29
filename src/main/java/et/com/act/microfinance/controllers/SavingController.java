package et.com.act.microfinance.controllers;

import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.SavingResponse;
import et.com.act.microfinance.services.SavingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
@RequiredArgsConstructor
@Tag(name = "Savings")
@CrossOrigin("http://localhost:4200/")
public class SavingController {
    private final SavingService savingService;

    @PostMapping("/generate")
    public PersistenceResponse generate(Long month, Double amount) {
        return savingService.generate(month, amount);
    }

    @GetMapping("/all")
    public List<SavingResponse> getAll() {
        return savingService.getAll();
    }
}
