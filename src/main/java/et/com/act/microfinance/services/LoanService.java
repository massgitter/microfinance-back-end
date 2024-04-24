package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Customer;
import et.com.act.microfinance.models.Loan;
import et.com.act.microfinance.models.Saving;
import et.com.act.microfinance.models.SavingPeriod;
import et.com.act.microfinance.repos.LoanRepo;
import et.com.act.microfinance.requests.LoanRequest;
import et.com.act.microfinance.responses.LoanResponse;
import et.com.act.microfinance.responses.PersistenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final CustomerService customerService;
    private final LoanRepo loanRepo;
    private final SavingService savingService;
    private final FiscalYearService fiscalYearService;

    public PersistenceResponse requestForLoan(LoanRequest request) {
        Customer customer = customerService.findById(request.getCustomerId());
        if (isEligibleForLoan(customer, request.getAmount())) {
            Loan loan = Loan.builder()
                    .customer(customer)
                    .amount(request.getAmount())
                    .interest(0d)
                    .issuedAt(LocalDateTime.now())
                    .remaining(request.getAmount())
                    .returnDate(LocalDateTime.now().plusDays(365))
                    .repaid(0d)
                    .build();
            List<Loan> loans = loanRepo.findByCustomer(customer);
            double remaining = loans.stream()
                    .mapToDouble(Loan::getRemaining).sum();
            if (remaining==0) {
                loanRepo.save(loan);
            }
            return new PersistenceResponse(true, "Success!");
        }

        return new PersistenceResponse(false, "You are not eligible for loan");
    }

    public boolean isEligibleForLoan(Customer customer, Double requestAmount) {
        List<Saving> savings = savingService.findLastSixMonthsPerCustomer(customer).stream()
                .filter(saving -> !saving.getSavingPeriod().getMonth().equals("DEFAULT"))
                .collect(Collectors.toList());

        List<String> months = savings.stream()
                .map(Saving::getSavingPeriod)
                .collect(Collectors.toList())
                .stream()
                .map(SavingPeriod::getMonth)
                .collect(Collectors.toList());

        double totalSaving = savings.stream()
                .mapToDouble(Saving::getTotalAmount)
                .sum();

        boolean areConsecutive = true;

        for (int i = 1; i < months.size(); i++) {
            String currentMonth = months.get(i);
            String previousMonth = months.get(i - 1);

            // Check if the current month is consecutive to the previous month
            if (!isConsecutive(previousMonth, currentMonth)) {
                areConsecutive = false;
                break;
            }
        }

        return areConsecutive && months.size() >=1 && totalSaving >= requestAmount;

    }

    private static boolean isConsecutive(String month1, String month2) {
        String[] monthsArray = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

        int index1 = getIndex(month1, monthsArray);
        int index2 = getIndex(month2, monthsArray);

        // Check if the months are consecutive in the array
        return Math.abs(index1 - index2) == 1 || Math.abs(index1 - index2) == 11;
    }

    private static int getIndex(String month, String[] monthsArray) {
        for (int i = 0; i < monthsArray.length; i++) {
            if (monthsArray[i].equalsIgnoreCase(month)) {
                return i;
            }
        }
        return -1; // If month not found
    }


    public List<LoanResponse> getAll() {
        return loanRepo.findAll()
                .stream()
                .map(Loan::loanResponse)
                .collect(Collectors.toList());
    }

    public PersistenceResponse loanRepayment(Long customerId, double amount) {
        Customer customer = customerService.findById(customerId);
        List<Loan> loans = loanRepo.findByCustomer(customer)
                .stream()
                .filter(loan -> loan.getRemaining() > 0)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            return new PersistenceResponse(false, "No active loans found for this customer!");
        }

        Loan loan = loans.get(0);

        if (amount > loan.getAmount()) {
            return new PersistenceResponse(false, "Amount exceeds remaining loan balance!");
        }

        if (LocalDateTime.now().isAfter(loan.getReturnDate())) {
            double interest = loan.getRemaining() * 0.02;
            loan.setInterest(loan.getInterest() + interest);
            loan.setAmount(loan.getAmount());
            loan.setRemaining(loan.getRemaining() - amount + interest);
        }

        loan.setRepaid(loan.getRepaid() + amount);

        loanRepo.save(loan);
        return new PersistenceResponse(true, "Payment processed successfully!");
    }


    public Loan findByLoanId(Long id) {
        return loanRepo.findById(id).orElseThrow(() -> new RuntimeException("No loan found!"));
    }
}
