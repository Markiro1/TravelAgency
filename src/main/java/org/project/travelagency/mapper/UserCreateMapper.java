package org.project.travelagency.mapper;

import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.model.User;

public class UserCreateMapper {

    public static User mapToModel(UserCreateDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstname())
                .lastName(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }

    public static UserCreateDto mapToDto(User user) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstname(user.getFirstName());
        userCreateDto.setLastname(user.getLastName());
        userCreateDto.setEmail(user.getEmail());
        userCreateDto.setPassword(user.getPassword());
        userCreateDto.setRole(user.getRole());
        return userCreateDto;
    }
}
