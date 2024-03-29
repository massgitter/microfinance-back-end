package et.com.act.microfinance.models;

import et.com.act.microfinance.responses.AddressResponse;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends Shared {
    private String city;
    private String woreda;
    private String kebele;
    private String houseNo;
    private String email;
    private String phone;

    public AddressResponse addressResponse() {
        return AddressResponse.builder()
                .city(city)
                .woreda(woreda)
                .kebele(kebele)
                .houseNo(houseNo)
                .email(email)
                .phone(phone)
                .build();
    }
}
