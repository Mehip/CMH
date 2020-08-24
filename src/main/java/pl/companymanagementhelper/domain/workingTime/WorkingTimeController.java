package pl.companymanagementhelper.domain.workingTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.companymanagementhelper.domain.workingTime.dto.WorkingTimeDto;
import pl.companymanagementhelper.utils.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "Working time controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class WorkingTimeController {
  private final WorkingTimeRepository workingTimeRepository;
  private final WorkingTimeService workingTimeService;

  @Value("${pl.cmh.app-name}")
  private String applicationName;
  private static final String ENTITY_NAME = "working_time";

  @Operation(summary = "Get list of all working times")
  @GetMapping("/working-time")
  public List<WorkingTimeDto> getAllWorkingTimes() {
    log.debug("REST request to get all Working Times");
    return workingTimeService.getAllWorkingTimeDtos();
  }

  @Operation(summary = "Get working time by id")
  @GetMapping("/working-time/{id}")
  public ResponseEntity getWorkingTimeById(
      @Parameter(required = true, description = "Working Time id - It's a value by which a work time is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to get Working Time : {}", id);
    return workingTimeService.getWorkingTimeDto(id).map((response) -> ResponseEntity.ok()
        .headers((HttpHeaders)null)
        .body(response))
        .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "Create new working time")
  @PostMapping("/working-time")
  public ResponseEntity<WorkingTimeDto> createWorkingTime(
      @Parameter(required = true, description = "Working time - It's an object which represent information about work time in a computer system") @RequestBody WorkingTimeDto workingTimeDto) throws URISyntaxException {
    log.debug("REST request to save Working Time {}", workingTimeDto);
    WorkingTime workingTime = workingTimeService.saveWorkingTimeDto(workingTimeDto);
    WorkingTimeDto result = workingTimeService.getWorkingTimeDto(workingTime).get();
    return ResponseEntity.created(new URI("/api/working-time/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Update working time")
  @PutMapping("/working-time")
  public ResponseEntity<WorkingTimeDto> updateWorkingTime(
      @Parameter(required = true, description = "Working time - It's an object which represent information about work time in a computer system") @RequestBody WorkingTimeDto workingTimeDto) {
    log.debug("REST request to update Working Time : {}", workingTimeDto);
    WorkingTime workingTime = workingTimeService.saveWorkingTimeDto(workingTimeDto);
    WorkingTimeDto result = workingTimeService.getWorkingTimeDto(workingTime).get();
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @Operation(summary = "Delete working time by id")
  @DeleteMapping("/working-time/{id}")
  public ResponseEntity<Void> deleteWorkingTimeById(
      @Parameter(required = true, description = "Working Time id - It's a value by which a work time is identified in a computer system") @PathVariable Long id) {
    log.debug("REST request to delete Working Time : {}", id);
    workingTimeRepository.deleteById(id);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
