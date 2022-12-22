package org.project.travelagency.dao;

import org.project.travelagency.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email);
    void addUser(User user);
}
