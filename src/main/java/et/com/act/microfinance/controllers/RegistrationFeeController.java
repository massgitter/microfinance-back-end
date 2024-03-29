package et.com.act.microfinance.controllers;

import et.com.act.microfinance.responses.RegistrationFeeResponse;
import et.com.act.microfinance.services.RegistrationFeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registrationFee")
@RequiredArgsConstructor
@Tag(name = "Registration Fee")
@CrossOrigin("http://localhost:4200/")
public class RegistrationFeeController {
    private final RegistrationFeeService registrationFeeService;

    @GetMapping("/all")
    public List<RegistrationFeeResponse> getAll(int page, int size) {
        return registrationFeeService.getAll(page, size);
    }
}
