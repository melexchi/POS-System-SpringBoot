package com.Melexworld.service.impl;

import com.Melexworld.domain.OrderStatus;
import com.Melexworld.domain.PaymentType;
import com.Melexworld.mapper.OrderMapper;
import com.Melexworld.model.*;
import com.Melexworld.payload.dto.OrderDTO;
import com.Melexworld.repository.OrderRepository;
import com.Melexworld.repository.ProductRepository;
import com.Melexworld.service.OrderService;
import com.Melexworld.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws Exception {
        User cashier=userService.getCurrentUser();

        Branch branch = cashier.getBranch();
        if(branch == null){
            throw  new Exception("Branch not found for the cashier");
        }

        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDTO.getCustomer())
                .paymentType(orderDTO.getPaymentType())
                .build();

        List<OrderItem> orderItems =orderDTO.getItems().stream().map(
                itemDTO ->{
                    Product product = productRepository.findById(itemDTO.getProductId()).orElseThrow(
                            ()-> new EntityNotFoundException("product not found")
                    );

                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemDTO.getQuantity())
                            .price(product.getSellingPrice() * itemDTO.getQuantity())
                            .order(order)
                            .build();
                }
        ).toList();
        double total= orderItems.stream().mapToDouble(
                OrderItem::getPrice
        ).sum();
        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) throws Exception {
        return orderRepository.findById(id)
                .map(OrderMapper::toDTO).orElseThrow(
                ()-> new Exception("Order not found with id " + id)
        );
    }

    @Override
    public List<OrderDTO> getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception {


        return orderRepository.findByBranchId(branchId).stream()
                .filter(
                        order -> customerId ==null || (order.getCustomer() != null && order.getCustomer().getId().equals(customerId))
                )
                .filter(
                        order -> cashierId == null || (order.getCashier() !=null && order.getCashier().getId().equals(cashierId))
                )
                .filter(
                        order -> paymentType ==null || order.getPaymentType()==paymentType
                )
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrderByCashier(Long cashierId) throws Exception {
        return orderRepository.findByCashierId(cashierId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {

        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new Exception("Order not found with id " + id)
        );
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception {

        LocalDate today =LocalDate.now();
        LocalDateTime start =today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        return orderRepository.findByBranchIdAndCreatedAtBetween(
                branchId, start, end
        ).stream().map(
                OrderMapper::toDTO
        ).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception {


        return orderRepository.findByCustomerId(customerId).stream().map(
                OrderMapper::toDTO
        ).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAll5RecentOrdersByBranchId(Long branchId) throws Exception {

        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream().map(
                OrderMapper::toDTO
        ).collect(Collectors.toList());
    }
}
