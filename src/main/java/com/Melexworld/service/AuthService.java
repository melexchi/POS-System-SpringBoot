package com.Melexworld.service;

import com.Melexworld.exceptions.UserException;
import com.Melexworld.payload.dto.UserDTO;
import com.Melexworld.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDTO userDto) throws UserException;
    AuthResponse login(UserDTO userDto) throws UserException;
}
