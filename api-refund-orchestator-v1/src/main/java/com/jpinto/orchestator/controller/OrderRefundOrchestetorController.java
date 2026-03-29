package com.jpinto.orchestator.controller;

import com.jpinto.orchestator.client.refund.dto.ApproveRefundRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.dto.ApprovedRefundResponse;
import com.jpinto.orchestator.dto.CreateOrderRefundRequest;
import com.jpinto.orchestator.services.ApproveOrderRefundWithCompensationService;
import com.jpinto.orchestator.services.OrderRefundOrchestetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/orders-refund")
@RequiredArgsConstructor
public class OrderRefundOrchestetorController {
    private final OrderRefundOrchestetorService orderRefundOrchestetorService;
    private final ApproveOrderRefundWithCompensationService approveOrderRefundWithCompensationService;


    @PostMapping()
    public RefundOrderResponse createOrderRefund(@RequestBody CreateOrderRefundRequest request){
        return orderRefundOrchestetorService.createOrderRefund(request);
    }

    @PutMapping("/approve")
    public ApprovedRefundResponse approveOrderRefund(@RequestBody ApproveRefundRequest approveRefundRequest){
        if(Objects.isNull(approveRefundRequest.orderRefundId())){
            throw new RuntimeException("Error. id orden no válido");
        }
        return  approveOrderRefundWithCompensationService.aproveOrderRefund(approveRefundRequest);
    }


}
