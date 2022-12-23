package org.project.travelagency.service.impl;

import org.project.travelagency.dao.UserDao;
import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.exception.NullEntityReferenceException;
import org.project.travelagency.mapper.OrderMapper;
import org.project.travelagency.mapper.UserCreateMapper;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.Role;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;


    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(UserCreateDto userDto) {
        if (userDto != null) {
            User user = UserCreateMapper.mapToModel(userDto);
            return userDao.save(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public void delete(Long id) {
        User user = readById(id);
        userDao.delete(user);
    }

    @Override
    public User update(UserCreateDto userDto) {
        if (userDto != null) {
            User user = UserCreateMapper.mapToModel(userDto);
            return userDao.update(user);
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
            throw new NullEntityReferenceException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }


    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
//        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void addUser(UserCreateDto userDto) {
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        userDto.setRole(Role.USER);
//
//        User user = UserCreateMapper.mapToModel(userDto);
//        userDao.addUser(user);
//    }
//
//    public Optional<User> findUserByEmail(String email) {
//        return userDao.findByEmail(email);
//    }

}
