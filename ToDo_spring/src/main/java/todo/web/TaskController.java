package todo.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import todo.entity.Task;
import todo.entity.User;
import todo.services.TaskService;
import todo.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/todo")
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @ModelAttribute(name = "task")
    public Task task(){
        return new Task();
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal){
        return userService.getByUsername(principal.getName());
    }

    @GetMapping
    public String displayTasks(Model model, Principal principal,
                               @RequestParam("page") Optional<Integer> page){
        //Pagination
        int currentPage = page.orElse(1);
        int pageSize = 5;

        //todo: пофиксить сортинг (не работает)
        Sort sorting = Sort.by(Sort.Order.desc("completed"));
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sorting);

        Page<Task> pageOfTasks = taskService.findPaginated(
                pageable,
                taskService.getAllByUserName(principal.getName()));

        model.addAttribute("taskPage", pageOfTasks);
        int totalPages = pageOfTasks.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/todo";
    }

    @PostMapping("/add")
    public String addTask(@Valid Task task, Errors errors, Principal principal){
        if (errors.hasErrors()) {
            return "redirect:/todo";
        }
        User user = userService.getByUsername(principal.getName());
        task.setUser(user);
        taskService.save(task);
        return "redirect:/todo";
    }

    @PostMapping("/delete")
    public String deleteTask(Long taskId){
        taskService.deleteById(taskId);
        return "redirect:/todo";
    }

    @PostMapping("/done")
    public String changeCompletionStatus(Long taskId){
        taskService.changeCompletionStatus(taskId);
        return "redirect:/todo";
    }

    @PostMapping("/update")
    public String update(Long taskId){
        taskService.changeUpdatingStatus(taskId);
        return "redirect:/todo";
    }

    @PostMapping("/applyChanges")
    public String applyChanges(Long taskId, String newValue){
        if (newValue.equals("")) {
            return "redirect:/todo";
        }
        taskService.changeValue(taskId, newValue);
        return "redirect:/todo";
    }

}
