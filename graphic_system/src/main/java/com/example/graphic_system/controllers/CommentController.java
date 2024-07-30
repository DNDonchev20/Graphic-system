package com.example.graphic_system.controllers;

import com.example.graphic_system.models.Comment;
import com.example.graphic_system.models.Task;
import com.example.graphic_system.models.User;
import com.example.graphic_system.services.CommentService;
import com.example.graphic_system.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/tasks/{taskId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllComments(@PathVariable Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        model.addAttribute("comments", commentService.getCommentsByTaskId(taskId));
        model.addAttribute("task", task);
        return "comments/list";
    }

    @GetMapping("/create")
    public String showCreateCommentForm(@PathVariable Long taskId, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("taskId", taskId);
        return "comments/create";
    }

    @PostMapping("/create")
    public String createComment(@PathVariable Long taskId, @ModelAttribute("comment") Comment comment, HttpSession session) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = (User) session.getAttribute("loggedInUser");
        comment.setTask(task);
        comment.setAuthor(user);
        comment.setCreatedDate(new Date());
        commentService.createComment(comment);
        return "redirect:/tasks/" + taskId + "/comments";
    }

    @GetMapping("/edit/{id}")
    public String showEditCommentForm(@PathVariable Long taskId, @PathVariable Long id, Model model) {
        Comment comment = commentService.getCommentById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        model.addAttribute("comment", comment);
        model.addAttribute("taskId", taskId);
        return "comments/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateComment(@PathVariable Long taskId, @PathVariable Long id, @ModelAttribute("comment") Comment commentDetails) {
        commentService.updateComment(id, commentDetails);
        return "redirect:/tasks/" + taskId + "/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long taskId, @PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/tasks/" + taskId + "/comments";
    }
}
