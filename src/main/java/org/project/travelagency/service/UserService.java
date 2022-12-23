package org.project.travelagency.service;

import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.model.User;

import java.util.Optional;

public interface UserService {
    void addUser(UserCreateDto userDto);

    Optional<User> findUserByEmail(String email);
}
