package com.example.mealserve.domain.customer.dto;

import com.example.mealserve.domain.customer.entity.Account;
import com.example.mealserve.domain.customer.entity.RoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountJoinRequestDto {

    private String email;
    private String password;
    private String address;
    private String phone;
    private RoleTypeEnum role;

    @Builder
    public Account toEntity() {
        return Account.builder()
                .email(email)
                .password(password)
                .phone(phone)
                .address(address)
                .role(role)
                .build();
    }
}
