package org.project.travelagency.service;

import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.dto.user.UserReadDto;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserCreateDto userDto);
    void delete(Long id);
    User update(UserCreateDto userDto);
    User readById(Long id);
    List<User> getAllUsers();

    Optional<User> findUserByEmail(String email);
}
