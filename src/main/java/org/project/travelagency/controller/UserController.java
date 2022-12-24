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
        System.out.println("Get-create ok");
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") UserCreateDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
//        System.out.println("Post-create ok");
//        System.out.println(userDto.getEmail());
        userDto.setRole(Role.USER);
//        System.out.println(userDto.getRole());
        userService.create(userDto);
//        System.out.println("Post-create ok");
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
        User oldUser = userService.readById(userId);

        if (result.hasErrors()) {
            userDto.setRole(oldUser.getRole());
            model.addAttribute("user", userDto);
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
