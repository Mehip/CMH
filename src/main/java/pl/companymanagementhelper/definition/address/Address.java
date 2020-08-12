package pl.companymanagementhelper.definition.address;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "t_address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "street")
  private String street;

  @Column(name = "house_number")
  private String houseNumber;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "city")
  private String city;

  public String getAddressDisplay() {
    return this.street + " " + this.houseNumber + " " + this.postalCode + " " + this.city;
  }
}
