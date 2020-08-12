package pl.companymanagementhelper.definition.workingTime;

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
public class WorkingTimeController {
  private final WorkingTimeRepository workingTimeRepository;
  private final WorkingTimeController workingTimeController;

  @Value("${pl.cmh.app-name}")
  private String applicationName;
  private static final String ENTITY_NAME = "working_time";

  @GetMapping("/working-time")
  public List<WorkingTime> getAllWorkingTimes() {
    log.debug("REST request to get all Working Times");
    return workingTimeRepository.findAll();
  }

  @GetMapping("/working-time/{id}")
  public ResponseEntity<WorkingTime> getWorkingTimeById(@PathVariable Long id) {
    log.debug("REST request to get Working Time : {}", id);
    Optional<WorkingTime> workingTime = workingTimeRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(workingTime);
  }

  @PostMapping("/working-time")
  public ResponseEntity<WorkingTime> createWorkingTime(@RequestBody WorkingTime workingTime) throws URISyntaxException {
    log.debug("REST request to save Working Time {}", workingTime);
    WorkingTime result = workingTimeRepository.save(workingTime);
    return ResponseEntity.created(new URI("/api/working-time/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/working-time")
  public ResponseEntity<WorkingTime> updateWorkingTime(@RequestBody WorkingTime workingTime) {
    log.debug("REST request to update Working Time : {}", workingTime);
    WorkingTime result = workingTimeRepository.save(workingTime);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @DeleteMapping("/working-time/{id}")
  public ResponseEntity<Void> deleteWorkingTimeById(@PathVariable Long id) {
    log.debug("REST request to delete Working Time : {}", id);
    workingTimeRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
