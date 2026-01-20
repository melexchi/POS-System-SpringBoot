package com.Melexworld.mapper;

import com.Melexworld.model.User;
import com.Melexworld.payload.dto.UserDTO;

public class UserMapper {


    public static UserDTO toDTO(User savedUser) {

        UserDTO userDto = new UserDTO();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setPhone(savedUser.getPhone());

        return  userDto;
    }
}
