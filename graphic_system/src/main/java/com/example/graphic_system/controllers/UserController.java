package com.example.graphic_system.controllers;

import com.example.graphic_system.models.User;
import com.example.graphic_system.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", getRoles());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, HttpSession session) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userService.createUser(user);
        session.setAttribute("loggedInUser", newUser);
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, HttpSession session, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            session.setAttribute("loggedInUser", existingUser);
            return "redirect:/home";
        } else {
            model.addAttribute("loginError", true);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", getRoles());
        return "users/create";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", getRoles());
        return "users/edit";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User userDetails) {
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        userService.updateUser(id, userDetails);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    private List<String> getRoles() {
        return Arrays.asList("Administrator", "Manager", "Team Member");
    }
}