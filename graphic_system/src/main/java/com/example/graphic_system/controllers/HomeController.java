package com.example.graphic_system.controllers;

import com.example.graphic_system.models.User;
import com.example.graphic_system.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        User user = userService.getUserById(sessionUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("projects", user.getProjects());
        return "home";
    }
}
