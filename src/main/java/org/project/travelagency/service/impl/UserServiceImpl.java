package org.project.travelagency.service.impl;

import org.project.travelagency.dao.UserDao;
import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.dto.user.UserUpdateDto;
import org.project.travelagency.exception.NullEntityReferenceException;
import org.project.travelagency.mapper.UserCreateMapper;
import org.project.travelagency.mapper.UserUpdateMapper;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(UserCreateDto userDto) {
        if (userDto != null) {
            User user = UserCreateMapper.mapToModel(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.save(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public User readById(Long id) {
        User user = userDao.getById(id);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User update(UserUpdateDto userDto) {
        String password;
        if (userDto != null) {
            User user = UserUpdateMapper.mapToModel(userDto);
            if (!user.getPassword().isBlank()){
                password = passwordEncoder.encode(user.getPassword());
            } else {
                password = readById(user.getId()).getPassword();
            }
            user.setPassword(password);
            return userDao.update(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public void delete(Long id) {
        User user = readById(id);
        userDao.delete(user);
    }
}