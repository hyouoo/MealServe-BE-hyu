package com.example.mealserve.domain.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderListRequestDto {

    private List<OrderRequestDto> orders;

    @Builder
    private OrderListRequestDto(List<OrderRequestDto> orders) {
        this.orders = orders;
    }

    public static OrderListRequestDto from(List<OrderRequestDto> orders) {
        return OrderListRequestDto.builder()
                .orders(orders)
                .build();
    }
}
