package todo.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import todo.entity.Task;


import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getTaskById(Long id);

    List<Task> getAllByUserId(Long id);

    List<Task> getAllByUser_Username(String username);

}
