package com.example.mealserve.domain.menu.dto;

import com.example.mealserve.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String image;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.image = menu.getImage();
    }
}
