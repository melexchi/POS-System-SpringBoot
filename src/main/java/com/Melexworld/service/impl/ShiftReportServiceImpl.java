package com.Melexworld.service.impl;

import com.Melexworld.domain.PaymentType;
import com.Melexworld.mapper.ShiftReportMapper;
import com.Melexworld.model.*;
import com.Melexworld.payload.dto.ShiftReportDTO;
import com.Melexworld.repository.BranchRepository;
import com.Melexworld.repository.OrderRepository;
import com.Melexworld.repository.RefundRepository;
import com.Melexworld.repository.ShiftReportRepository;
import com.Melexworld.service.ShiftReportService;
import com.Melexworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;

    private final BranchRepository branchRepository;

    private final OrderRepository orderRepository;

    private final RefundRepository refundRepository;

    private final UserService userService;

    @Override
    public ShiftReportDTO startShift(Long cashierId, Long branchId, LocalDateTime shiftStart) throws Exception {

        User currentUser = userService.getCurrentUser();

        shiftStart = LocalDateTime.now();
        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existingShift = shiftReportRepository.findByCashierAndShiftStartBetween(
                currentUser, startOfDay,endOfDay
        );
        if(existingShift.isPresent()){
            throw new Exception("Shift already started for today");
        }

        Branch branch =currentUser.getBranch();

        ShiftReport shiftReport = ShiftReport.builder().cashier(currentUser).shiftStart(shiftStart).branch(branch).build();

        ShiftReport savedShiftReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDTO(savedShiftReport);
    }

    @Override
    public ShiftReportDTO endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {

        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport= shiftReportRepository.findTopByCashierIdAndShiftEndIsNullOrderByShiftStartDesc(currentUser).orElseThrow(
                ()-> new Exception("No active shift found for the cashier")
        );

        shiftReport.setShiftEnd(shiftEnd);

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()
        );

        double totalRefunds = refunds.stream().mapToDouble(refund -> refund.getAmount()!=null? refund.getAmount():0.0).sum();

        List<Order> orders =orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()
        );

        double totalSales =orders.stream().mapToDouble(Order::getTotalAmount).sum();

        int totalOrders = orders.size();

        double netSales =totalSales-totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);

        shiftReport.setTotalSales(totalSales);

        shiftReport.setTotalOrders(totalOrders);

        shiftReport.setNetSales(netSales);

        shiftReport.setRecentOrders(getRecentOrders(orders));

        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));

        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));

        shiftReport.setRefunds(refunds);


        return null;
    }



    @Override
    public ShiftReportDTO getShiftReportById(Long id) throws Exception {
        return null;
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        return List.of();
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByCashierId(Long cashierId) {
        return List.of();
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId) {
        return List.of();
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception {
        return null;
    }

    @Override
    public ShiftReportDTO getShiftReportByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {
        return null;
    }




    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {

        Map<PaymentType, List<Order>> grouped= orders.stream().collect(Collectors.groupingBy(order -> order.getPaymentType()!=null?order.getPaymentType():PaymentType.CASH));

        List<PaymentSummary> summaries= new ArrayList<>();

        for(Map.Entry<PaymentType, List<Order>> entry: grouped.entrySet()){
            double amount= entry.getValue().stream().mapToDouble(Order::getTotalAmount).sum();

            int transactions =entry
                    .getValue().size();

            double percentage = (amount/totalSales)*100;

            PaymentSummary ps =new PaymentSummary();

            ps.setType(entry.getKey());
            ps.setTotalAmount(amount);
            ps.setTransactionCount(transactions);
            ps.setPercentage(percentage);
            summaries.add(ps);

        }

        return summaries;
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product,Integer> productSalesMap =new HashMap<>();


        for(Order order:orders){
            for(OrderItem item: order.getItems()){
                Product product =item.getProduct();
                productSalesMap.put(product,productSalesMap.getOrDefault(product,0)+item.getQuantity());
            }
        }

        return productSalesMap.entrySet().stream().sorted((a,b)->b.getValue().compareTo(a.getValue())).limit(5).map(Map.Entry::getKey).collect(Collectors.toList());

    }

    private List<Order> getRecentOrders(List<Order> orders) {

        return orders.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed()).limit(5).collect(Collectors.toList());
    }
}
