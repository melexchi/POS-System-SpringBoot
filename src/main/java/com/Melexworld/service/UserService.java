package com.Melexworld.service;

import com.Melexworld.exceptions.UserException;
import com.Melexworld.model.User;

import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserById(Long id) throws Exception, UserException;
    List<User> getAllUsers();
}
