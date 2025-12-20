package com.Melexworld.controller;

import com.Melexworld.exceptions.UserException;
import com.Melexworld.mapper.UserMapper;
import com.Melexworld.model.User;
import com.Melexworld.payload.dto.UserDto;
import com.Melexworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
       User user=  userService.getUserFromJwtToken(jwt);

       return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, Exception {
        User user=  userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
