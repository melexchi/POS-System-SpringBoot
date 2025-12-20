package com.Melexworld.service;

import com.Melexworld.exceptions.UserException;
import com.Melexworld.payload.dto.UserDto;
import com.Melexworld.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
