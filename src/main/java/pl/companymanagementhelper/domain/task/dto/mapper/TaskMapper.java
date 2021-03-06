package pl.companymanagementhelper.domain.task.dto.mapper;

import pl.companymanagementhelper.domain.task.Task;
import pl.companymanagementhelper.domain.task.dto.TaskDto;

public class TaskMapper {
  public static TaskDto getDtoFromEntity(Task entity) {
    if (entity == null) {
      return null;
    }
    return TaskDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .createDate(entity.getCreateDate())
        .startDateTime(entity.getStartDateTime())
        .endDateTime(entity.getEndDateTime())
        .author(entity.getAuthor().getId())
        .worker(entity.getWorker().getId())
        .build();
  }
}
