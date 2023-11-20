package com.example.mealserve.domain.customer.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String email;
    private String password;
    private String phone;
    private String address;
    private Long point;

    @Enumerated(EnumType.STRING)
    private RoleTypeEnum role;

    @Builder
    public Account(String email,
                   String password,
                   String phone,
                   String address,
                   RoleTypeEnum role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.point = role == RoleTypeEnum.Customer ? 1000000L : 0L;

    }
}
