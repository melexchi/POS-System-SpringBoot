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
        userDto.setBranchId(savedUser.getBranch() != null ? savedUser.getBranch().getId() : null);

        userDto.setStoreId(savedUser.getStore()!=null? savedUser.getStore().getId():null);

        return userDto;
    }

    public static User toEntity(UserDTO userDTO) {
        User createdUser = new User();
        createdUser.setEmail(userDTO.getEmail());
        createdUser.setFullName(userDTO.getFullName());
        createdUser.setPhone(userDTO.getPhone());
        createdUser.setRole(userDTO.getRole());
        createdUser.setCreatedAt(userDTO.getCreatedAt());
        createdUser.setUpdatedAt(userDTO.getUpdatedAt());
        createdUser.setLastLogin(userDTO.getLastLogin());
        createdUser.setPassword(userDTO.getPassword());

        return createdUser;
    }
}
