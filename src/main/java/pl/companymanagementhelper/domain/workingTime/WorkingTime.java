package pl.companymanagementhelper.domain.workingTime;

import lombok.*;
import pl.companymanagementhelper.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_working_times")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "start_date_time")
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time")
  private LocalDateTime endDateTime;

  @ManyToOne
  @JoinColumn(name = "worker_id")
  private User worker;
}
