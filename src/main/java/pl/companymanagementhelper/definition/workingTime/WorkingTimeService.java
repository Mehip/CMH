package pl.companymanagementhelper.definition.workingTime;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.companymanagementhelper.definition.user.UserRepository;
import pl.companymanagementhelper.definition.workingTime.dto.WorkingTimeDto;
import pl.companymanagementhelper.definition.workingTime.dto.mapper.WorkingTimeMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkingTimeService {
  private final WorkingTimeRepository workingTimeRepository;
  private final UserRepository userRepository;

  public List<WorkingTimeDto> getAllWorkingTimeDtos(){
    return workingTimeRepository.findAll().stream().map(WorkingTimeMapper::getDtoFromEntity).collect(Collectors.toList());
  }

  public Optional<WorkingTimeDto> getWorkingTimeDto(WorkingTime workingTime) {
    return Optional.ofNullable(WorkingTimeMapper.getDtoFromEntity(workingTime));
  }

  public Optional<WorkingTimeDto> getWorkingTimeDto(Long id) {
    return getWorkingTimeDto(workingTimeRepository.getOne(id));
  }

  public WorkingTime saveWorkingTimeDto(WorkingTimeDto dto) {
    WorkingTime def = new WorkingTime();
    if (dto.getId() != null) {
      Optional<WorkingTime> material = workingTimeRepository.findById(dto.getId());
      if (material.isPresent()) {
        def = material.get();
      }
    }

    def.setStartDateTime(dto.getStartDateTime());
    def.setEndDateTime(dto.getEndDateTime());
    def.setWorker(userRepository.getOne(dto.getId()));

    return workingTimeRepository.save(def);
  }
}
