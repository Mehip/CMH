package pl.companymanagementhelper.definition.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.companymanagementhelper.definition.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "creation_date")
  private LocalDateTime createDate;

  @Column(name = "start_date_time")
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time")
  private LocalDateTime endDateTime;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @ManyToOne
  @JoinColumn(name = "worker_id")
  private User worker;
}
