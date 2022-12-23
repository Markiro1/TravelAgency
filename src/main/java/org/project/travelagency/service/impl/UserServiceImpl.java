package org.project.travelagency.service.impl;

import org.project.travelagency.dao.UserDao;
import org.project.travelagency.dto.user.UserCreateDto;
import org.project.travelagency.mapper.UserCreateMapper;
import org.project.travelagency.model.Role;
import org.project.travelagency.model.User;
import org.project.travelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void addUser(UserCreateDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(Role.USER);

        User user = UserCreateMapper.mapToModel(userDto);
        userDao.addUser(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
