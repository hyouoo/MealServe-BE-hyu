package com.example.mealserve.domain.order.dto;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.order.entity.DeliverStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderListResponseDto {

    private final Long accountId;
    private final String address;

    private final List<OrderDto> menus;
    private final LocalDateTime createdAt;

    private final int totalPrice;
    private final DeliverStatus status;

    @Builder
    private OrderListResponseDto(Long accountId, String address, List<OrderDto> menus,
                                 LocalDateTime createdAt, int totalPrice, DeliverStatus status) {
        this.accountId = accountId;
        this.address = address;
        this.menus = menus;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public static OrderListResponseDto of(Account account, List<OrderDto> menus, int totalPrice) {
        return OrderListResponseDto.builder()
                .accountId(account.getId())
                .address(account.getAddress())
                .menus(menus)
                .createdAt(menus.get(0).getCreatedAt())
                .totalPrice(totalPrice)
                .status(menus.get(0).getStatus())
                .build();
    }
}
