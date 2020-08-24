package pl.companymanagementhelper.definition.task;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.companymanagementhelper.definition.task.dto.TaskDto;
import pl.companymanagementhelper.utils.HeaderUtil;
import pl.companymanagementhelper.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Task controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
  private final TaskRepository taskRepository;
  private final TaskService taskService;

  @Value("${pl.cmh.app-name}")
  private String applicationName;
  private static final String ENTITY_NAME = "task";

  @Operation(summary = "Get list of all task")
  @GetMapping("/tasks")
  public List<TaskDto> getAllTask() {
    log.debug("REST request to get all Task");
    return taskService.getAllTaskDtos();
  }

  @Operation(summary = "Get task by id")
  @GetMapping("/tasks/{id}")
  public ResponseEntity<TaskDto> getTaskById(
      @Parameter(required = true, description = "Task id - It's a value by which a task is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to get Task: {}", id);
    Optional<TaskDto> taskDto = taskService.getTaskDto(id);
    return ResponseUtil.wrapOrNotFound(taskDto);
  }

  @Operation(summary = "Create new task")
  @PostMapping("/tasks")
  public ResponseEntity<TaskDto> createTask(
      @Parameter(required = true, description = "TaskDto - It's an object which represent information about task in a computer system") @RequestBody TaskDto taskDto) throws URISyntaxException {
    log.debug("REST request to save Task: {}", taskDto);
    Task task = taskService.saveTaskDto(taskDto);
    TaskDto result = taskService.getTaskDto(task).get();
    return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Update task")
  @PutMapping("/tasks")
  public ResponseEntity<TaskDto> updateTask(
      @Parameter(required = true, description = "TaskDto - It's an object which represent information about task in a computer system") @RequestBody TaskDto taskDto) {
    log.debug("REST request to update Task: {}", taskDto);
    Task task = taskService.saveTaskDto(taskDto);
    TaskDto result = taskService.getTaskDto(task).get();
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Delete task by id")
  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<Void> deleteTaskById(
      @Parameter(required = true, description = "Task id - It's a value by which a task is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to delete Task : {}", id);
    taskRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
