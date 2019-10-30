package ostap.storoshchuk.nerdytask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ostap.storoshchuk.nerdytask.controller.GlobalController;
import ostap.storoshchuk.nerdytask.dto.request.TaskRequest;
import ostap.storoshchuk.nerdytask.dto.response.TaskResponse;
import ostap.storoshchuk.nerdytask.entity.Task;
import ostap.storoshchuk.nerdytask.entity.User;
import ostap.storoshchuk.nerdytask.exeption.WrongInputDataException;
import ostap.storoshchuk.nerdytask.repository.TaskRepository;
import ostap.storoshchuk.nerdytask.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GlobalController globalController;
    @Autowired
    private UserRepository userRepository;

    public void shareTask(String email, Integer id) throws WrongInputDataException {
        User user = userRepository.findByEmail(email);
        Task task = findById(id);
        task.setUser(user);
        StringBuilder sb = new StringBuilder();
        sb.append(task.getDescription()).append(" sent from ").append(globalController.getLoginUser().getEmail());
        task.setDescription(sb.toString());
        taskRepository.save(task);
        System.out.println("Done");
    }

    public void deleteById(Integer id) {
        taskRepository.deleteById(id);
    }

    public TaskResponse update(Integer id, String title, String description) {
        Task task = taskRepository.findOneById(id);
        if (title != null) task.setTitle(title);
        if (description != null) task.setDescription(description);
        taskRepository.save(task);
        return new TaskResponse(task);
    }

    public Task findById(Integer id) throws WrongInputDataException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        }
        throw new WrongInputDataException("Person with id : " + id + " not found");
    }

    public void create(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setUser(globalController.getLoginUser());
        taskRepository.save(task);
    }

    public List<TaskResponse> getAllByUsername() {
        return taskRepository.findAllByUsersUsername(globalController.getLoginUser().getUsername())
                .stream().map(task -> new TaskResponse(task)).collect(Collectors.toList());
    }
}
