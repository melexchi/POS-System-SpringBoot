package com.Melexworld.payload.dto;


import com.Melexworld.model.ShiftReport;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RefundDTO {

    private Long id;

    private OrderDTO orderDTO;
    private Long orderId;

    private String reason;

    private Double amount;

//    private ShiftReport shiftReport;
    private Long shiftReportId;

    private UserDTO cashier;
    private String cashierName;

    private BranchDTO branch;
    private Long branchId;

    private String paymentType;

    private LocalDateTime createdAt;
}
