package com.Melexworld.payload.dto;

import com.Melexworld.model.Branch;
import com.Melexworld.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDTO {

    private Long id;

    private Long branchId;

    private Long productId;

    private BranchDTO branch;

    private ProductDTO product;

    private Integer quantity;

    private LocalDateTime lastUpdated;
}
