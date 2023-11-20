package com.example.mealserve.domain.order.entity;

import lombok.Getter;

@Getter
public enum DeliverStatus {

    PREPARE("출발전"),
    COMPLETE("배달완료");

    private final String description;

    DeliverStatus(String description) {
        this.description = description;
    }
}
