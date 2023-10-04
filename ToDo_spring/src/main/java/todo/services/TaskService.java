package todo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo.data.TaskRepository;
import todo.entity.Task;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepo;

    @Autowired
    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public void save(Task task){
        taskRepo.save(task);
    }

    public void deleteById(Long taskId) {
        taskRepo.deleteById(taskId);
    }

    public List<Task> getAllByUserName(String username) {
        return taskRepo.getAllByUser_Username(username);
    }

    public void changeCompletionStatus(Long taskId) {
        if (taskRepo.existsById(taskId)) {
            Task task = taskRepo.getTaskById(taskId);
            task.setCompleted(!task.isCompleted());
            taskRepo.save(task);
        } else {
            throw new RuntimeException("This task does not exist");
        }
    }

    public void changeUpdatingStatus(Long taskId) {
        if (taskRepo.existsById(taskId)) {
            Task task = taskRepo.getTaskById(taskId);
            task.setUpdating(true);
            taskRepo.save(task);
        } else {
            throw new RuntimeException("This task does not exist");
        }
    }

    public void changeValue(Long taskId, String newValue) {
        if (taskRepo.existsById(taskId)) {
            Task task = taskRepo.getTaskById(taskId);
            task.setValue_of_task(newValue);
            task.setUpdating(false);
            taskRepo.save(task);
        } else {
            throw new RuntimeException("This task does not exist");
        }
    }

    public Page<Task> findPaginated(Pageable pageable, List<Task> tasks) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Task> list;

        if (tasks.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, tasks.size());
            list = tasks.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasks.size());
    }

}
