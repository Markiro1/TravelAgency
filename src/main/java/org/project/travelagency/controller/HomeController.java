package org.project.travelagency.controller;

import org.project.travelagency.exception.NullEntityReferenceException;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String home(Principal principal) {
        Optional<User> user = userService.findUserByEmail(principal.getName());
        if (user.isPresent()) {
            return "redirect:/orders/create/" + user.get().getId();
        }
        throw new NullEntityReferenceException("User not found");
    }
}
