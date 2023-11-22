package com.example.mealserve.domain.account.dto;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.account.entity.AccountRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginResponseDto {

    private final Long id;
    private final String email;
    private final AccountRole role;

    @Builder
    private LoginResponseDto(Long id, String email, AccountRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public static LoginResponseDto from(Account account) {
        return LoginResponseDto.builder()
            .id(account.getId())
            .email(account.getEmail())
            .role(account.getRole())
            .build();
    }
}
