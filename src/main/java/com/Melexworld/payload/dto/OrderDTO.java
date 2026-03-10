package com.Melexworld.payload.dto;


import com.Melexworld.domain.PaymentType;
import com.Melexworld.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class OrderDTO {

    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private Long branchId;
    private Long customerId;

    private BranchDTO branch;

    private UserDTO cashier;

    private UserDTO user;

    private Customer customer;

    private PaymentType paymentType;

    private List<OrderItemDTO> items;
}
