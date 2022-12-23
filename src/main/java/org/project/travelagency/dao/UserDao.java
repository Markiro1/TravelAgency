package org.project.travelagency.dao;

import org.project.travelagency.model.Order;
import org.project.travelagency.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);
    User update(User user);
    User getById(Long id);
    List<User> getAll();
}
