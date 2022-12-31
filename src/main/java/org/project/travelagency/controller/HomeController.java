package org.project.travelagency.controller;

import org.project.travelagency.model.Role;
import org.project.travelagency.model.User;
import org.project.travelagency.security.UserDetailsImpl;
import org.project.travelagency.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String toHome(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getRole().equals(Role.MANAGER)) {
            return "admin-tools";
        }
        return "redirect:/orders/create/" + user.getId();
    }
}