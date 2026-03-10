package com.Melexworld.payload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {

    private Long id;

    private Double price;

    private ProductDTO product;

    private Long productId;

    private Integer quantity;


    private Long orderId;
}
