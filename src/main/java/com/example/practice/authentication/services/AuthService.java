package com.example.practice.authentication.services;


import com.example.practice.authentication.exception.BusinessException;
import com.example.practice.authentication.exception.ErrorCodes;
import com.example.practice.authentication.mappers.UserMapper;
import com.example.practice.authentication.model.User;
import com.example.practice.authentication.model.entities.UserDao;
import com.example.practice.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(User signUp) {
        Optional<UserDao> userDao = userRepository.findByEmail(signUp.getEmail());

        if(userDao.isPresent())
            throw new BusinessException(ErrorCodes.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);

        UserDao newUser = UserDao.builder().email(signUp.getEmail()).password(passwordEncoder.encode(signUp.getPassword())).build();
        newUser = userRepository.save(newUser);
        return UserMapper.mapToDto(newUser);
    }
}
