package com.Melexworld.mapper;

import com.Melexworld.model.Order;
import com.Melexworld.model.Product;
import com.Melexworld.model.Refund;
import com.Melexworld.model.ShiftReport;
import com.Melexworld.payload.dto.OrderDTO;
import com.Melexworld.payload.dto.ProductDTO;
import com.Melexworld.payload.dto.RefundDTO;
import com.Melexworld.payload.dto.ShiftReportDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {

    public static ShiftReportDTO toDTO(ShiftReport entity){

        return ShiftReportDTO.builder()
                .id(entity.getId())
                .shiftEnd(entity.getShiftEnd())
                .shiftStart(entity.getShiftStart())
                .totalSales(entity.getTotalSales())
                .totalRefunds(entity.getTotalRefunds())
                .netSales(entity.getNetSales())
                .totalOrders(entity.getTotalOrders())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier() !=null?entity.getCashier().getId():null)
                .branchId(entity.getBranch().getId())
                .recentOrders(mapOrders(entity.getRecentOrders()))
                .topSellingProducts(mapProducts(entity.getTopSellingProducts()))
                .refunds(mapRefunds(entity.getRefunds()))
                .paymentSummaries(entity.getPaymentSummaries())
                .build();
    }

    private static List<RefundDTO> mapRefunds(List<Refund> refunds) {
        if(refunds == null || refunds.isEmpty()){return null;}
        return refunds.stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    private static List<ProductDTO> mapProducts(List<Product> topSellingProducts) {
        if(topSellingProducts ==null || topSellingProducts.isEmpty()){return null;}

        return topSellingProducts.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    private static List<OrderDTO> mapOrders(List<Order> recentOrders) {
        if(recentOrders ==null || recentOrders.isEmpty()){return null;}

        return recentOrders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }
}
