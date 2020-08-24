package pl.companymanagementhelper.domain.workingTime.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class WorkingTimeDto {
  private Long id;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Long worker;
}
