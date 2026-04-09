package com.jpinto.orchestator.controller;

import com.jpinto.orchestator.client.refund.dto.ApproveRefundRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.client.refund.dto.RejectRefundRequest;
import com.jpinto.orchestator.controller.document.OrdenReembolsoControllerDoc;
import com.jpinto.orchestator.dto.ApprovedRefundResponse;
import com.jpinto.orchestator.dto.CreateOrderRefundRequest;
import com.jpinto.orchestator.dto.RejectRefundOrchestatorRequest;
import com.jpinto.orchestator.services.ApproveOrderRefundWithCompensationService;
import com.jpinto.orchestator.services.CreateOrderRefundOrchestetorService;
import com.jpinto.orchestator.services.RejectOrderRefundOrchestatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/orders-refund")
@RequiredArgsConstructor
public class OrderRefundOrchestetorController implements OrdenReembolsoControllerDoc {
    private final CreateOrderRefundOrchestetorService orderRefundOrchestetorService;
    private final ApproveOrderRefundWithCompensationService approveOrderRefundWithCompensationService;
    private final RejectOrderRefundOrchestatorService rejectOrderRefundOrchestatorService;


    @PostMapping()
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public RefundOrderResponse createOrderRefund(@RequestBody CreateOrderRefundRequest request){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Set<String> authorities = securityContext
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        log.info("----------------------------------------");
        authorities.forEach(log::info);
        log.info("----------------------------------------");

        return orderRefundOrchestetorService.createOrderRefund(request);
    }

    @PutMapping("/approve")
    @PreAuthorize("hasAuthority('SUPERVISOR')")
    public ApprovedRefundResponse approveOrderRefund(@RequestBody ApproveRefundRequest approveRefundRequest){
        if(Objects.isNull(approveRefundRequest.orderRefundId())){
            throw new IllegalArgumentException("Error. id orden no válido");
        }
        return  approveOrderRefundWithCompensationService.aproveOrderRefund(approveRefundRequest);
    }



    @PutMapping("/reject")
    @PreAuthorize("hasAuthority('SUPERVISOR')")
    public RefundOrderResponse rejectOrderRefund(@RequestBody RejectRefundOrchestatorRequest rejectRefundRequest){
        if(Objects.isNull(rejectRefundRequest.orderRefundId())){
            throw new IllegalArgumentException("Error. id orden no válido");
        }
        return rejectOrderRefundOrchestatorService.rejectOrderRefund(rejectRefundRequest);
    }
}
