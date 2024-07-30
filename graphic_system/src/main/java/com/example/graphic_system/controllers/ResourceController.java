package com.example.graphic_system.controllers;

import com.example.graphic_system.models.Resource;
import com.example.graphic_system.models.Task;
import com.example.graphic_system.services.ResourceService;
import com.example.graphic_system.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks/{taskId}/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllResources(@PathVariable Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        model.addAttribute("resources", resourceService.getResourcesByTaskId(taskId));
        model.addAttribute("task", task);
        return "resources/list";
    }

    @GetMapping("/create")
    public String showCreateResourceForm(@PathVariable Long taskId, Model model) {
        model.addAttribute("resource", new Resource());
        model.addAttribute("taskId", taskId);
        return "resources/create";
    }

    @PostMapping("/create")
    public String createResource(@PathVariable Long taskId, @ModelAttribute("resource") Resource resource) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        resource.setTask(task);
        resourceService.createResource(resource);
        return "redirect:/tasks/" + taskId + "/resources";
    }

    @GetMapping("/edit/{id}")
    public String showEditResourceForm(@PathVariable Long taskId, @PathVariable Long id, Model model) {
        Resource resource = resourceService.getResourceById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        model.addAttribute("resource", resource);
        model.addAttribute("taskId", taskId);
        return "resources/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateResource(@PathVariable Long taskId, @PathVariable Long id, @ModelAttribute("resource") Resource resourceDetails) {
        resourceService.updateResource(id, resourceDetails);
        return "redirect:/tasks/" + taskId + "/resources";
    }

    @GetMapping("/delete/{id}")
    public String deleteResource(@PathVariable Long taskId, @PathVariable Long id) {
        resourceService.deleteResource(id);
        return "redirect:/tasks/" + taskId + "/resources";
    }
}
