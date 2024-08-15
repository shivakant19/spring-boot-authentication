package com.example.practice.authentication.mappers;

import com.example.practice.authentication.model.User;
import com.example.practice.authentication.model.entities.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserMapper {

    public static User mapToDto(UserDao userDao) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(userDao,User.class);
    }
}
