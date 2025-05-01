package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceInterface;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class UsersController {

    private final UserServiceInterface userService;

    @Autowired
    public UsersController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        UserDetails user = userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", userService.getRoles());
        return "dashboard";
    }

    @GetMapping("/show")
    public String show(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.getRoles());
        return "admin/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user, Model model) {
        userService.createUser(user);
        return "redirect:/admin/index";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", userService.getRoles());
        return "admin/edit";
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("id") Integer id){
        userService.updateUser(id, user);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/")
    public String delete(@RequestParam("id") Integer id, Model model){
        userService.deleteUserById(id);
        return "redirect:/admin/index";
    }

    @GetMapping()
    public String showUserInfo(Principal principal, Model model) {
        UserDetails user = userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "admin/admin";
    }
}
