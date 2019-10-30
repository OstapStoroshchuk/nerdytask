package ostap.storoshchuk.nerdytask.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ostap.storoshchuk.nerdytask.dto.request.TaskRequest;
import ostap.storoshchuk.nerdytask.dto.request.UserRequest;
import ostap.storoshchuk.nerdytask.dto.response.TaskResponse;
import ostap.storoshchuk.nerdytask.dto.response.UserResponse;
import ostap.storoshchuk.nerdytask.exeption.WrongInputDataException;
import ostap.storoshchuk.nerdytask.service.TaskService;
import ostap.storoshchuk.nerdytask.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping("/shareTask")
    public String shareTask(@RequestParam String email,
                            @RequestParam Integer taskId,
                            Model model) {
        System.out.println(email + " " + taskId);
        try {
            model.addAttribute("task", new TaskResponse(taskService.findById(taskId)));
            taskService.shareTask(email, taskId);
        } catch (WrongInputDataException e) {
            e.printStackTrace();
        }
        return "redirect:/showTasks";
    }

    @GetMapping("/shareTask/{id}")
    public String shareTaskPage(@PathVariable Integer id, Model model) throws WrongInputDataException {
        model.addAttribute("task", new TaskResponse(taskService.findById(id)));
        return "share";
    }

    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") Integer id, Model model) {
        taskService.deleteById(id);
        model.addAttribute("tasks", taskService.getAllByUsername());
        return "redirect:/showTasks";
    }

    @GetMapping("/showTasks")
    public String showTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllByUsername());
        return "index";
    }

    @PostMapping("/updateTask")
    public String updateTask(@RequestParam Integer id,
                             @RequestParam String title,
                             @RequestParam String description) {
        taskService.update(id, title, description);
        return "redirect:/showTasks";
    }

    @GetMapping("/singleTask/{id}")
    public String singleTask(@PathVariable Integer id, Model model) throws WrongInputDataException {
        model.addAttribute("task", new TaskResponse(taskService.findById(id)));
        return "update";
    }

    @PostMapping("/addTask")
    public String addTask(TaskRequest taskRequest, Model model) {
        taskService.create(taskRequest);
        return "redirect:/showTasks";
    }

    @PostMapping("/saveUser")
    public String saveUser(UserRequest userRequest) {
        userService.create(userRequest);
        return "redirect:/login";
    }


    @PostMapping("/successURL")
    public String successURL(Model model) {
        List<TaskResponse> tasks = new ArrayList<>(taskService.getAllByUsername());
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
