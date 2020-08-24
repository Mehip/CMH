package pl.companymanagementhelper.domain.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.companymanagementhelper.domain.task.dto.TaskDto;
import pl.companymanagementhelper.domain.task.dto.mapper.TaskMapper;
import pl.companymanagementhelper.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  public List<TaskDto> getAllTaskDtos(){
    return taskRepository.findAll().stream().map(TaskMapper::getDtoFromEntity).collect(Collectors.toList());
  }

  public Optional<TaskDto> getTaskDto(Task task) {
    return Optional.ofNullable(TaskMapper.getDtoFromEntity(task));
  }

  public Optional<TaskDto> getTaskDto(Long id) {
    return getTaskDto(taskRepository.getOne(id));
  }

  public Task saveTaskDto(TaskDto dto) {
    Task def = new Task();
    if (dto.getId() != null) {
      Optional<Task> material = taskRepository.findById(dto.getId());
      if (material.isPresent()) {
        def = material.get();
      }
    }

    def.setName(dto.getName());
    def.setDescription(dto.getDescription());
    def.setCreateDate(dto.getCreateDate());
    def.setStartDateTime(dto.getStartDateTime());
    def.setEndDateTime(dto.getEndDateTime());
    def.setAuthor(userRepository.getOne(dto.getId()));
    def.setWorker(userRepository.getOne(dto.getId()));

    return taskRepository.save(def);
  }
}
