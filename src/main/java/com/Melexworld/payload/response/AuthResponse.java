package com.Melexworld.payload.response;

import com.Melexworld.payload.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDTO user;


}
