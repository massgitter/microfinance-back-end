package et.com.act.microfinance.controllers;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.requests.LoanRequest;
import et.com.act.microfinance.responses.LoanResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.services.LoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loan")
@RequiredArgsConstructor
@Tag(name = "Loan")
@CrossOrigin("http://localhost:4200/**")
public class LoanController {
    private final LoanService loanService;


    @PostMapping("/request")
    public PersistenceResponse loanRequest(@RequestBody LoanRequest request) {
        return loanService.requestForLoan(request);
    }

    @GetMapping("/all")
    public List<LoanResponse> getAll() {
        return loanService.getAll();
    }

    @PostMapping("/repayment")
    public PersistenceResponse loanRepayment(Long customerId, double amount) {
        return loanService.loanRepayment(customerId, amount);
    }

    @GetMapping("/byId/{id}")
    public LoanResponse findLoanById(@PathVariable("id") Long id) {
        return loanService.findByLoanId(id).loanResponse();
    }
}
