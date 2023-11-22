package com.example.mealserve.domain.order.entity;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "`order`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private DeliverStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Builder
    private Order(Account account, Menu menu, Integer quantity, DeliverStatus status) {
        this.account = account;
        this.menu = menu;
        this.quantity = quantity;
        this.status = status;
    }

    public static Order of(Account account, Menu menu, Integer quantity, DeliverStatus status) {
        return Order.builder()
                .account(account)
                .menu(menu)
                .quantity(quantity)
                .status(status)
                .build();
    }

    public void complete() {
        this.status = DeliverStatus.COMPLETE;
    }
}
