package org.project.travelagency.controller;

import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.dto.user.UserUpdateDto;
import org.project.travelagency.mapper.UserUpdateMapper;
import org.project.travelagency.model.Role;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") UserCreateDto userDto, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
        userDto.setRole(Role.USER);
        User u = userService.create(userDto);
        User user = userService.readById(u.getId());
        return "redirect:/orders/create/" + user.getId();
    }

    @GetMapping("/{user_id}/read")
    public String read(@PathVariable("user_id") long userId, Model model) {
        User user = userService.readById(userId);
        model.addAttribute("user", UserUpdateMapper.mapToDto(user));
        return "user-info";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-list";
    }

    @GetMapping("/{user_id}/update")
    public String update(@PathVariable("user_id") long userId, Model model) {
        User user = userService.readById(userId);
        model.addAttribute("user", UserUpdateMapper.mapToDto(user));
        model.addAttribute("roles", Role.values());
        return "update-user";
    }

    @PostMapping("/{user_id}/update")
    public String update(@PathVariable("user_id") long userId, Model model,
                         @Validated @ModelAttribute("user") UserUpdateDto userDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("user", UserUpdateMapper.mapToDto(userService.readById(userId)));
            model.addAttribute("roles", Role.values());
            return "update-user";
        }
        userService.update(userDto);
        return "redirect:/users/" + userId + "/read";
    }

    @GetMapping("/{user_id}/delete")
    public String delete(@PathVariable("user_id") long userId) {
        userService.delete(userId);
        return "redirect:/users/all";
    }
}