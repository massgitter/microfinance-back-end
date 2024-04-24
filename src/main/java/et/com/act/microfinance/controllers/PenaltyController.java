package et.com.act.microfinance.controllers;

import et.com.act.microfinance.models.Penalty;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.services.PenaltyService;
import et.com.act.microfinance.utils.RolesEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("penalty")
@RequiredArgsConstructor
@Tag(name = "Penalty")
@SecurityRequirement(name = "microfinance")
@RolesAllowed(RolesEnum.Roles.ADMIN)
public class PenaltyController {
    private final PenaltyService penaltyService;

    @PostMapping("/create")
    public PersistenceResponse create(Double amount, Long penaltyMethod) {
        return penaltyService.create(amount, penaltyMethod);
    }

    @GetMapping("/all")
    public List<Penalty> getAll() {
        return penaltyService.getAll();
    }
}
