package com.example.mealserve.domain.order.dto;

import com.example.mealserve.domain.order.entity.DeliverStatus;
import com.example.mealserve.domain.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private final Long menuId;
    private final String name;
    private final Integer quantity;
    private final Integer price;
    private final String image;
    private final LocalDateTime createdAt;
    private final DeliverStatus status;

    @Builder
    private OrderDto(Long menuId, String name, Integer quantity, Integer price,
                     String image, LocalDateTime createdAt, DeliverStatus status) {
        this.menuId = menuId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static OrderDto fromCustomer(Order order) {
        return OrderDto.builder()
                .menuId(order.getMenu().getId())
                .name(order.getMenu().getName())
                .quantity(order.getQuantity())
                .price(order.getMenu().getPrice() * order.getQuantity())
                .build();
    }

    public static OrderDto fromOwner(Order order) {
        return OrderDto.builder()
                .menuId(order.getMenu().getId())
                .name(order.getMenu().getName())
                .quantity(order.getQuantity())
                .price(order.getMenu().getPrice() * order.getQuantity())
                .image(order.getMenu().getImage())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .build();
    }
}
