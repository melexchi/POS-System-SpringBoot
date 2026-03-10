package com.Melexworld.controller;


import com.Melexworld.model.Refund;
import com.Melexworld.payload.dto.RefundDTO;
import com.Melexworld.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;


    @PostMapping
    public ResponseEntity<RefundDTO> createRefund(@RequestBody RefundDTO refundDTO) throws Exception {
        RefundDTO refund =refundService.createRefund(refundDTO);

        return ResponseEntity.ok(refund);
    }

    @GetMapping
    public ResponseEntity<List<RefundDTO>> getAllRefunds() throws Exception {

       List<RefundDTO> refund= refundService.getAllRefunds();

        return ResponseEntity.ok(refund);
    }


    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>>getRefundByCashier(@PathVariable Long cashierId) throws Exception {

        List<RefundDTO> refund= refundService.getRefundByCashier(cashierId);

        return ResponseEntity.ok(refund);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getRefundByBranch(@PathVariable Long branchId) throws Exception {

        List<RefundDTO> refund= refundService.getRefundByBranch(branchId);

        return ResponseEntity.ok(refund);
    }

    @GetMapping("/shift/{shiftId}")
    public ResponseEntity<List<RefundDTO>> getRefundByShift(@PathVariable Long shiftId) throws Exception {

        List<RefundDTO> refund= refundService.getRefundByShiftReport(shiftId);

        return ResponseEntity.ok(refund);
    }

    @GetMapping("/cashier/{cashierId}/date-range")

    public ResponseEntity<List<RefundDTO>> getRefundByCashierAndDateRange(@PathVariable Long cashierId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws Exception {

        List<RefundDTO> refund = refundService.getRefundByCashierAndDateRange(cashierId,startDate,endDate);

        return ResponseEntity.ok(refund);
    }

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long refundId) throws Exception {

        RefundDTO refund = refundService.getRefundById(refundId);

        return ResponseEntity.ok(refund);
    }



}
