package com.example.mealserve.domain.customer.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleTypeEnum {
    Customer(Authority.Customer),
    Owner(Authority.Owner);

    private final String authority;

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String Customer = "ROLE_Customer";
        public static final String Owner = "ROLE_Owner";

    }
}
