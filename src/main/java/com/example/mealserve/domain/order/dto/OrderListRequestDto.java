package com.example.mealserve.domain.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderListRequestDto {

    @NotNull
    @Valid
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
