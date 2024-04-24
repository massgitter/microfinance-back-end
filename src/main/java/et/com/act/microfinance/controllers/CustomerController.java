package et.com.act.microfinance.controllers;

import et.com.act.microfinance.requests.CustomerRequest;
import et.com.act.microfinance.responses.CustomerResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import et.com.act.microfinance.services.CustomerService;
import et.com.act.microfinance.utils.RolesEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@Tag(name = "Customer")
@SecurityRequirement(name = "microfinance")
@RolesAllowed(RolesEnum.Roles.ADMIN)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(value = "/import", consumes = "multipart/form-data")
    public PersistenceResponse importCustomers(@RequestParam("file") MultipartFile multipartFile) {
        return customerService.importCustomers(multipartFile);
    }

    @PostMapping("/register")
    public PersistenceResponse register(@RequestBody CustomerRequest request) {
        return customerService.register(request);
    }

    @PutMapping("/update")
    public PersistenceResponse update(@RequestBody CustomerRequest request) {
        return customerService.update(request);
    }

    @GetMapping("/byId/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return customerService.findById(id).customerResponse();
    }

    @GetMapping("/all")
    public List<CustomerResponse> getAll(int page, int size) {
        return customerService.getAll(page, size);
    }

    @GetMapping("/count")
    public Long countOfCustomers() {
        return customerService.countOfCustomers();
    }
}
