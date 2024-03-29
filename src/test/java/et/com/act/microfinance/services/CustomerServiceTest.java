package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Address;
import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Profession;
import et.com.act.microfinance.repos.CustomerRepo;
import et.com.act.microfinance.repos.StatusRepo;
import et.com.act.microfinance.requests.AddressRequest;
import et.com.act.microfinance.requests.CustomerRequest;
import et.com.act.microfinance.responses.PersistenceResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private StatusRepo statusRepo;

    @MockBean
    private CustomerRepo customerRepo;

    @MockBean
    private SavingService savingService;

    @MockBean
    private ProfessionService professionService;

    @MockBean
    private RegistrationFeeService registrationFeeService;


    @Test
    void register_SuccessfulRegistration() {

        // Mock dependencies
        when(customerRepo.findByIdCard(customerRequest().getIdCard())).thenReturn(null);
        when(customerRepo.save(customer())).thenReturn(customer());

        // Call the service method
        PersistenceResponse response = customerService.register(customerRequest());

        // Verify interactions
        verify(customerRepo, times(1)).save(customer());
        verify(registrationFeeService, times(1)).create(customer());
        verify(savingService, times(1)).create(customer());
        verify(professionService, times(1)).create(profession().getDescription());

        // Assert the result
        assertThat(response).isEqualTo(new PersistenceResponse(true, "Successfully Registered"));
    }

    @Test
    void register_CustomerAlreadyRegistered() {
        // Mock dependency
        when(customerRepo.findByIdCard(customerRequest().getIdCard())).thenReturn(mock(Customer.class));

        // Call the service method
        PersistenceResponse response = customerService.register(customerRequest());

        // Assert the result
        assertThat(response).isEqualTo(new PersistenceResponse(false, String.format("customer with id %s is already registered", customerRequest().getIdCard())));
    }

    @Test
    void register_ExceptionThrownDuringRegistration() {
        // Mock dependencies to throw an exception
        when(customerRepo.findByIdCard(customerRequest().getIdCard())).thenReturn(null);
        when(customerRepo.save(any(Customer.class))).thenThrow(new RuntimeException("Simulated error"));

        // Call the service method
        PersistenceResponse response = customerService.register(customerRequest());

        assertThat(response).isEqualTo(new PersistenceResponse(false, "Something went wrong, Failed To Register Customer"));

    }

    private AddressRequest aRequest() {
        return AddressRequest.builder()
                .city("Addis Ababa")
                .woreda("13")
                .kebele("Selam Amba")
                .phone("+251923568975")
                .build();
    }

    private Address address() {
        return Address.builder()
                .city(aRequest().getCity())
                .woreda(aRequest().getWoreda())
                .kebele(aRequest().getKebele())
                .phone(aRequest().getPhone())
                .build();
    }

    private CustomerRequest customerRequest() {
        return  CustomerRequest.builder()
                .firstName("Abebe")
                .middleName("Beso")
                .lastName("Bela")
                .idCard("a3b/act/24")
                .aRequest(aRequest())
                .profession("Devops Engineer")
                .build();
    }

    private Customer customer() {
        return Customer.builder()
                .firstName(customerRequest().getFirstName())
                .middleName(customerRequest().getMiddleName())
                .lastName(customerRequest().getLastName())
                .idCard(customerRequest().getIdCard())
                .address(address())
                .profession(profession())
                .status(statusRepo.findById(4L))
                .build();
    }


    private Profession profession() {
        return Profession.builder()
                .description("Devops Engineer")
                .build();
    }

}