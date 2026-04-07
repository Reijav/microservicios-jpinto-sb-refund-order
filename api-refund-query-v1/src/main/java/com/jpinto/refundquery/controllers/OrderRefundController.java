package com.jpinto.refundquery.controllers;

import com.jpinto.refundquery.controllers.dto.RefundOrderDto;
import com.jpinto.refundquery.service.OrderRefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders-refund-query")
@RequiredArgsConstructor
public class OrderRefundController {
    private final OrderRefundService orderRefundService;

    @GetMapping("/{id}")
    public ResponseEntity<RefundOrderDto> getRefundOrder(@PathVariable String id){
        return ResponseEntity.ok(orderRefundService.getById(id));
    }

}
