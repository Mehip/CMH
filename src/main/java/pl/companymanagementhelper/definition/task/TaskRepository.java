package pl.companymanagementhelper.definition.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByAuthorId(Long id);
  List<Task> findAllByWorkerId(Long id);
}
