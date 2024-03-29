package et.com.act.microfinance.models;


import et.com.act.microfinance.responses.CustomerResponse;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends Shared {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String idCard;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "profession")
    private Profession profession;

    @ManyToOne
    @JoinColumn(name = "address")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "sector")
    private Sector sector;

    public CustomerResponse customerResponse() {
        return CustomerResponse.builder()
                .id(getId())
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .idCard(idCard)
                .status(status.getName())
                .pResponse(profession.professionResponse())
                .aResponse(address.addressResponse())
                .build();
    }
}
