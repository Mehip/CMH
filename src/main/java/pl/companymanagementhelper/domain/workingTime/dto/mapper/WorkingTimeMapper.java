package pl.companymanagementhelper.domain.workingTime.dto.mapper;


import pl.companymanagementhelper.domain.workingTime.WorkingTime;
import pl.companymanagementhelper.domain.workingTime.dto.WorkingTimeDto;

public class WorkingTimeMapper {
  public static WorkingTimeDto getDtoFromEntity(WorkingTime entity) {
    if (entity == null) {
      return null;
    }
    return WorkingTimeDto.builder()
        .id(entity.getId())
        .startDateTime(entity.getStartDateTime())
        .endDateTime(entity.getEndDateTime())
        .worker(entity.getWorker().getId())
        .build();
  }
}
