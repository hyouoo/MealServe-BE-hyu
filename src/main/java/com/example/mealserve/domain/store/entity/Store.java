package com.example.mealserve.domain.store.entity;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.menu.entity.Menu;
import com.example.mealserve.domain.store.dto.StoreRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String tel;

    @OneToOne
    @JoinColumn(name = "account_id") // TODO: 실행확인 (referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @Builder
    private Store(String name, String address, String tel, Account account) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.account = account;
    }

    public static Store of(StoreRequestDto requestDto, Account account) {
        return Store.builder()
            .name(requestDto.getName())
            .address(requestDto.getAddress())
            .tel(requestDto.getTel())
            .account(account)
            .build();
    }

    public void update(StoreRequestDto storeRequestDto) {
        this.name = storeRequestDto.getName();
        this.address = storeRequestDto.getAddress();
        this.tel = storeRequestDto.getTel();
    }
}
