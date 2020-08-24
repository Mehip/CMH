package pl.companymanagementhelper.definition.task.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TaskDto {
  private Long id;
  private String name;
  private String description;
  private LocalDateTime createDate;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Long author;
  private Long worker;
}
