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
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String getSuccessPage(Principal principal) {
        Optional<User> user = userService.findUserByEmail(principal.getName());
        if (user.isPresent()) {
            return "redirect:/orders/create/" + user.get().getId();
        }
        throw new NullEntityReferenceException("User not found");
    }
}
