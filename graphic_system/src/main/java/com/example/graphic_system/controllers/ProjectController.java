package com.example.graphic_system.controllers;

import com.example.graphic_system.models.Project;
import com.example.graphic_system.models.Task;
import com.example.graphic_system.models.User;
import com.example.graphic_system.services.ProjectService;
import com.example.graphic_system.services.TaskService;
import com.example.graphic_system.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping
    public String getAllProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects/list";
    }

    @GetMapping("/create")
    public String showCreateProjectForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("users", userService.getAllUsers());
        return "projects/create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute("project") Project project, @RequestParam(name = "userIds", required = false) List<Long> userIds) {
        List<User> users = (userIds != null) ? userService.getUsersByIds(userIds) : List.of();
        project.setUsers(users);
        projectService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{id}")
    public String showEditProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getAllUsers());
        return "projects/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute("project") Project projectDetails, @RequestParam(name = "userIds", required = false) List<Long> userIds) {
        List<User> users = (userIds != null) ? userService.getUsersByIds(userIds) : List.of();
        projectDetails.setUsers(users);
        projectService.updateProject(id, projectDetails);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model, HttpSession session) {
        Project project = projectService.getProjectById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        User user = (User) session.getAttribute("loggedInUser");
        List<Task> tasks = project.getTasks();

        model.addAttribute("project", project);
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        model.addAttribute("users", userService.getAllUsers());

        return "projects/view";
    }

    @PostMapping("/{projectId}/tasks/create")
    public String createTask(@PathVariable Long projectId,
                             @ModelAttribute("task") Task task,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                             HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!user.getRole().equals("Administrator") && !user.getRole().equals("Manager")) {
            return "redirect:/projects/" + projectId;
        }

        Project project = projectService.getProjectById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(project);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        taskService.createTask(task);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{projectId}/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!user.getRole().equals("Administrator") && !user.getRole().equals("Manager")) {
            return "redirect:/projects/" + projectId;
        }

        taskService.deleteTask(taskId);
        return "redirect:/projects/" + projectId;
    }
}