package com.example.graphic_system.controllers;

import com.example.graphic_system.models.Comment;
import com.example.graphic_system.models.Task;
import com.example.graphic_system.models.User;
import com.example.graphic_system.services.CommentService;
import com.example.graphic_system.services.TaskService;
import com.example.graphic_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/{taskId}")
    public String viewTask(@PathVariable Long projectId, @PathVariable Long taskId, Model model, HttpSession session) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = (User) session.getAttribute("loggedInUser");
        List<Comment> comments = commentService.getCommentsByTaskId(taskId);

        model.addAttribute("task", task);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());

        return "tasks/view";
    }

    @PostMapping("/{taskId}/comments/create")
    public String createComment(@PathVariable Long projectId, @PathVariable Long taskId, @ModelAttribute("comment") Comment comment, HttpSession session) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = (User) session.getAttribute("loggedInUser");
        comment.setTask(task);
        comment.setAuthor(user);
        comment.setCreatedDate(new Date());
        commentService.createComment(comment);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId;
    }
}
