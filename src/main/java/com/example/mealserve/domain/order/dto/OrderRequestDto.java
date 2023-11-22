package com.example.mealserve.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequestDto {

    @NotNull
    private Long menuId;

    @NotNull
    @Min(value = 1, message = "1인분 이상 입력해 주세요.")
    private Integer quantity;

    @Builder
    private OrderRequestDto(Long menuId, Integer quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static OrderRequestDto of(Long menuId, Integer quantity) {
        return OrderRequestDto.builder()
                .menuId(menuId)
                .quantity(quantity)
                .build();
    }
}
