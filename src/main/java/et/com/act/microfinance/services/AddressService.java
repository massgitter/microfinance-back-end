package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Address;
import et.com.act.microfinance.repos.AddressRepo;
import et.com.act.microfinance.requests.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;

    public Address create(AddressRequest request) {
        Address address = Address.builder()
                .city(request.getCity())
                .woreda(request.getWoreda())
                .kebele(request.getKebele())
                .phone(request.getPhone())
                .build();
        return addressRepo.save(address);
    }
}
