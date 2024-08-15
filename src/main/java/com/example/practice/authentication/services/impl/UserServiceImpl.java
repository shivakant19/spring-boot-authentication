package com.example.practice.authentication.services.impl;


import com.example.practice.authentication.mappers.UserMapper;
import com.example.practice.authentication.model.User;
import com.example.practice.authentication.model.entities.UserDao;
import com.example.practice.authentication.repositories.UserRepository;
import com.example.practice.authentication.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getUserByEmail(String email) {
        Optional<UserDao> userDao = userRepository.findByEmail(email);
        return userDao.map(UserMapper::mapToDto).orElse(null);
    }
}
