package com.example.mealserve.domain.store.dto;

import com.example.mealserve.domain.menu.dto.MenuResponseDto;
import com.example.mealserve.domain.menu.entity.Menu;
import com.example.mealserve.domain.store.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreResponseDto {
    private String name;
    private String address;
    private String tel;
    private List<MenuResponseDto> menus = new ArrayList<>();

    public StoreResponseDto(Store store, List<MenuResponseDto> menus) {
        this.name = store.getName();
        this.address = store.getAddress();
        this.menus = menus;
    }

    public StoreResponseDto(Store store) {
        this.name = store.getName();
        this.address = store.getAddress();
        this.tel = store.getTel();
    }

    public static StoreResponseDto from(Store store) {
        return new StoreResponseDto(store);
    }

}