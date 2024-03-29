package et.com.act.microfinance.models;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerCsv extends Shared{
    private String firstName;
    private String middleName;
    private String lastName;
    private String idCard;
    private String city;
    private String woreda;
    private String kebele;
    private String phone;
    private String profession;

}
