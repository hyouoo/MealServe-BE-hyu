package com.example.mealserve.domain.order;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.account.entity.AccountRole;
import com.example.mealserve.domain.order.dto.OrderListResponseDto;
import com.example.mealserve.domain.order.dto.OrderRequestDto;
import com.example.mealserve.domain.order.dto.OrderResponseDto;
import com.example.mealserve.domain.order.entity.DeliverStatus;
import com.example.mealserve.global.tool.LoginAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{storeId}/orders")
    public OrderResponseDto orderIn(@PathVariable Long storeId,
                                    @RequestBody @Validated List<OrderRequestDto> requestDtoList,
                                    @LoginAccount Account customer) {
        return orderService.orderIn(storeId, requestDtoList, customer);
    }

    @Secured(AccountRole.Authority.OWNER)
    @GetMapping("/orders")
    public ResponseEntity<List<OrderListResponseDto>> getOrders(@LoginAccount Account owner) {
        List<OrderListResponseDto> responseBody = orderService.getOrders(owner);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @Secured(AccountRole.Authority.OWNER)
    @PutMapping("/orders/{accountId}")
    public ResponseEntity<String> completeOrders(@LoginAccount Account owner, @PathVariable Long accountId) {
        orderService.completeOrders(owner, accountId);
        String responseBody = DeliverStatus.COMPLETE.getDescription();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
