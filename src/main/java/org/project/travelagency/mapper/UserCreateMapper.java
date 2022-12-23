package org.project.travelagency.mapper;

import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.model.User;

public class UserCreateMapper {

    public static User mapToModel(UserCreateDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .lastName(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }
}
