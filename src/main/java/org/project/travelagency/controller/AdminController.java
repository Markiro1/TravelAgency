package org.project.travelagency.controller;

import org.project.travelagency.dto.user.UserReadDto;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAdminUtils(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-tools";
    }

    @GetMapping("orders/create")
    public String createOrder(Model model) {
        UserReadDto userDto = new UserReadDto();
        List<User> users = userService.getAllUsers();
        model.addAttribute("user", userDto);
        model.addAttribute("users", users);
        return "create-order-admin";
    }

    @PostMapping("orders/create")
    public String createOrder(@ModelAttribute("userDto") UserReadDto userDto) {
        return "redirect:/orders/create/" + userDto.getId();
    }

}
