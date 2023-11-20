package com.example.mealserve.domain.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountLoginRequestDto {

    private String email;
    private String password;
}
