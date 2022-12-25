package org.project.travelagency.service;

import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.dto.user.UserUpdateDto;
import org.project.travelagency.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(UserCreateDto userDto);

    User readById(Long id);

    List<User> getAllUsers();

    Optional<User> findUserByEmail(String email);

    User update(UserUpdateDto userDto);

    void delete(Long id);
}