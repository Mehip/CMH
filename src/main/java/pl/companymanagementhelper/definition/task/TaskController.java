package pl.companymanagementhelper.definition.task;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.companymanagementhelper.utils.HeaderUtil;
import pl.companymanagementhelper.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

  @GetMapping("/tasks")
  public List<Task> getAllTasks() {
    log.debug("REST request to get all Tasks");
    return taskRepository.findAll();
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    log.debug("REST request to get Task : {}", id);
    Optional<Task> task = taskRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(task);
  }

  @PostMapping("/tasks")
  public ResponseEntity<Task> createTask(@RequestBody Task task) throws URISyntaxException {
    log.debug("REST request to save Task : {}", task);
    Task result = taskRepository.save(task);
    return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/tasks")
  public ResponseEntity<Task> updateTask(@RequestBody Task task) {
    log.debug("REST request to update Task : {}", task);
    Task result = taskRepository.save(task);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
    log.debug("REST request to delete Task : {}", id);
    taskRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
