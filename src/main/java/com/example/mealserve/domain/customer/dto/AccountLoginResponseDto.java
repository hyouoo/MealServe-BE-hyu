package com.example.mealserve.domain.customer.dto;

import com.example.mealserve.domain.customer.entity.RoleTypeEnum;
import lombok.Getter;

@Getter
public class AccountLoginResponseDto {

    private RoleTypeEnum role;
}
