package org.project.travelagency.controller;


import org.project.travelagency.dao.impl.UserDaoImpl;
import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.dto.user.UserUpdateDto;
import org.project.travelagency.mapper.UserCreateMapper;
import org.project.travelagency.mapper.UserUpdateMapper;
import org.project.travelagency.model.Role;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.project.travelagency.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{user_id}/read")
    public String read(@PathVariable("user_id") long userId, Model model) {
        User user = userService.readById(userId);
        model.addAttribute("user", UserUpdateMapper.mapToDto(user));
        return "user-info";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") UserCreateDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
        userDto.setRole(Role.USER);
        userService.create(userDto);
        //return "home";
        return "user-info";
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
        if (userDto.getPassword().isBlank()) {
            userDto.setPassword(userService.readById(userId).getPassword());
        }
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
        return "redirect:/login";
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-list";
    }

/*        public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();
        UserServiceImpl userService = new UserServiceImpl(userDao);

        UserCreateDto userDto = new UserCreateDto();

        userDto.setFirstname("User2");
        userDto.setLastname("Test2");
        userDto.setEmail("user2@gmail.com");
        userDto.setPassword("Qwerty_123");
        userDto.setRole(Role.MANAGER);

        userService.create(userDto);
//        System.out.println(userService.readById(1L).getEmail());
//
//            userDto.setId(1L);
//            userDto.setRole(Role.MANAGER);
//            userService.update(userDto);
//
//        System.out.println(userService.getAllUsers().get(0).getEmail());

//            userService.delete(1L);
    }*/

}
