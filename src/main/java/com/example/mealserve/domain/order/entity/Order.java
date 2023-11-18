package com.example.mealserve.domain.order.entity;

import com.example.mealserve.domain.customer.entity.Customer;
import com.example.mealserve.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "order")
public class Order extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private DeliverStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    @Builder
    private Order(Customer customer, Menu menu, Integer quantity) {
        this.customer = customer;
        this.menu = menu;
        this.quantity = quantity;
        this.status = DeliverStatus.PREPARE;
    }

    public static Order from(Customer customer, Menu menu, Integer quantity) {
        return Order.builder()
                .customer(customer)
                .menu(menu)
                .quantity(quantity)
                .build();
    }

    public void complete(Order order) {
        this.status = DeliverStatus.COMPLETE;
    }
}
