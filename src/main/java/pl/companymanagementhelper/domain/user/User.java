package pl.companymanagementhelper.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.companymanagementhelper.domain.address.Address;
import pl.companymanagementhelper.config.UserRole;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "surname")
  private String surname;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "Salary")
  private BigDecimal salary;

  @Column(name = "role")
  private UserRole role;

  @Column(name = "contract_type")
  private Double contractType;

  @OneToOne
  @JoinColumn(name = "address_id")
  private Address address;
}