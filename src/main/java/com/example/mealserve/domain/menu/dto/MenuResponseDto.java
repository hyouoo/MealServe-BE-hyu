package com.example.mealserve.domain.menu.dto;

import com.example.mealserve.domain.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuResponseDto {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    @Builder
    private MenuResponseDto(Long id, String name, Integer price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static MenuResponseDto from(Menu menu) {
        return MenuResponseDto.builder()
            .id(menu.getId())
            .name(menu.getName())
            .price(menu.getPrice())
            .image(menu.getImage())
            .build();
    }
}
