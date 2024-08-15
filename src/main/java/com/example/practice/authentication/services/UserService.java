package com.example.practice.authentication.services;

import com.example.practice.authentication.model.User;

public interface UserService {
    User getUserByEmail(String email);
}
