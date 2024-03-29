package et.com.act.microfinance.controllers;

import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.responses.ProfessionResponse;
import et.com.act.microfinance.services.ProfessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profession")
@RequiredArgsConstructor
@Tag(name = "Profession")
@CrossOrigin("http://localhost:4200/")
public class ProfessionController {
    private final ProfessionService professionService;

    @PostMapping("/create")
    public PersistenceResponse create(@RequestBody String description) {
        return professionService.create(description);
    }

    @GetMapping("/byDescription/{description}")
    public ProfessionResponse byDescription(@PathVariable("description") String description) {
        return professionService.findByDescription(description).professionResponse();
    }

    @GetMapping("/all")
    public List<ProfessionResponse> getAll() {
        return professionService.getAll();
    }
}
