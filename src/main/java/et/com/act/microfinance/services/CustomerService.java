package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Address;
import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.repos.CustomerRepo;
import et.com.act.microfinance.repos.OnboardingFeeRepo;
import et.com.act.microfinance.repos.StatusRepo;
import et.com.act.microfinance.requests.CustomerRequest;
import et.com.act.microfinance.responses.CustomerResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final AddressService addressService;
    private final ProfessionService professionService;
    private final StatusRepo statusRepo;
    private final OnboardingFeeRepo onboardingFeeRepo;
    private final SavingService savingService;
    private final EntityManager entityManager;
    private final RegistrationFeeService registrationFeeService;

    public PersistenceResponse importCustomers(MultipartFile multipartFile) {
        try {
            importCsvToCustomer(multipartFile);
            Session session = entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                PreparedStatement statement = connection.prepareStatement("select import_customers()");
                statement.execute();
            });

            return new PersistenceResponse(true, "Successfully Imported!");
        } catch (HibernateException e) {
            e.printStackTrace();
            return new PersistenceResponse(false, "Failed To import Customers!");
        }
    }

    PersistenceResponse importCsvToCustomer(MultipartFile multipartFile) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/microfinance", "mastewal", "mas1985");
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into customercsv(createdon, updatedon, city, firstname, idcard, kebele, lastname, middlename, phone, profession, woreda) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {
                String path = System.getProperty("jboss.home.dir").concat(File.separator).concat("welcome-content").concat(File.separator).concat("imports")
                        .concat(File.separator).concat(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                connection.setAutoCommit(false);

                FileOutputStream outputStream = new FileOutputStream(path);
                outputStream.write(multipartFile.getBytes());
                outputStream.close();
                outputStream.flush();

                BufferedReader reader = new BufferedReader(new FileReader(path));
                String lineText;
                //skip column header
                reader.readLine();

                while ((lineText = reader.readLine()) != null) {
                    String[] data = lineText.split(",");

                    statement.setDate(1, null);
                    statement.setDate(2, null);
                    statement.setString(3, data[4]);
                    statement.setString(4, data[0]);
                    statement.setString(5, data[3]);
                    statement.setString(6, data[6]);
                    statement.setString(7, data[2]);
                    statement.setString(8, data[1]);
                    statement.setString(9, data[7]);
                    statement.setString(10, data[8]);
                    statement.setString(11, data[5]);

                    statement.addBatch();
                }

                statement.executeBatch();
            }
            connection.commit();
            return new PersistenceResponse(true, "Success");

        } catch (SQLException | IOException e) {
            return new PersistenceResponse(false, e.getMessage());
        }
    }

    public PersistenceResponse register(CustomerRequest request) {
        Customer byIdCard = customerRepo.findByIdCard(request.getIdCard());
        if (Objects.isNull(byIdCard)) {
            try {
                Address address = addressService.create(request.getARequest());

                professionService.create(request.getProfession());

                Customer customer = Customer.builder()
                        .firstName(request.getFirstName())
                        .middleName(request.getMiddleName())
                        .lastName(request.getLastName())
                        .idCard(request.getIdCard())
                        .status(statusRepo.findById(4L))
                        .profession(professionService.findByDescription(request.getProfession()))
                        .address(address)
                        .build();

                customerRepo.save(customer);

                registrationFeeService.create(customer);
                savingService.create(customer);


                return new PersistenceResponse(true, "Successfully Registered");
            } catch (Exception e) {
                return new PersistenceResponse(false, "Something went wrong, Failed To Register Customer");
            }
        } else
            return new PersistenceResponse(false, String.format("customer with id %s is already registered", request.getIdCard()));

    }

    public PersistenceResponse update(CustomerRequest request) {
        try {
            Customer customer = customerRepo.findByIdCard(request.getIdCard());

            customer.setFirstName(request.getFirstName());
            request.setMiddleName(request.getMiddleName());
            customer.setLastName(request.getLastName());
            customer.setIdCard(request.getIdCard());
            customer.setProfession(professionService.findByDescription(request.getProfession()));
            return new PersistenceResponse(true, "Success on update!");
        } catch (Exception e) {
            return new PersistenceResponse(false, "Failed to update!");
        }
    }

    public Customer findById(Long id) {
        return Objects.requireNonNull(customerRepo.findById(id).orElse(null));
    }

    public List<CustomerResponse> getAll(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return customerRepo.findAll()
                .stream()
                .map(Customer::customerResponse)
                .sorted(Comparator.comparing(CustomerResponse::getFirstName))
                .sorted(Comparator.comparing(CustomerResponse::getMiddleName))
                .collect(Collectors.toList());

    }

    public Long countOfCustomers() {
        return (long) customerRepo.findAll().size();
    }

}
