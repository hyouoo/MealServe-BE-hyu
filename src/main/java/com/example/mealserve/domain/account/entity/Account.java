package com.example.mealserve.domain.account.entity;

import com.example.mealserve.domain.account.dto.SignupRequestDto;
import com.example.mealserve.domain.store.entity.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long point;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Store store;

    @Builder
    private Account(String email, String password, String phone, String address, AccountRole role, Long point) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.point = point;
    }

    public static Account from(SignupRequestDto requestDto, String password) {
        return Account.builder()
            .email(requestDto.getEmail())
            .password(password)
            .phone(requestDto.getPhone())
            .address(requestDto.getAddress())
            .role(requestDto.isOwner() ? AccountRole.OWNER : AccountRole.CUSTOMER)
            .point(requestDto.isOwner() ? 0L : 1000000L)
            .build();
    }
}
