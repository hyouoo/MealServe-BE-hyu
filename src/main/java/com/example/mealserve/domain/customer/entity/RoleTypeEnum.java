package com.example.mealserve.domain.customer.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleTypeEnum {
    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER);

    private final String authority;

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_Customer";
        public static final String OWNER = "ROLE_Owner";

    }
}
