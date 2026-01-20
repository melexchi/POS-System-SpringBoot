package com.Melexworld.payload.dto;

import com.Melexworld.domain.StoreStatus;
import com.Melexworld.model.StoreContact;
import com.Melexworld.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {


    private Long id;


    private  String brand;


    private UserDto storeAdmin;

    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;


    private String description;

    private String storeType;


    private StoreStatus status;


    private StoreContact contact;
}
