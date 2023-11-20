package com.example.mealserve.domain.customer.dto;

import com.example.mealserve.domain.customer.entity.RoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountLoginResponseDto {

    private RoleTypeEnum role;
}
