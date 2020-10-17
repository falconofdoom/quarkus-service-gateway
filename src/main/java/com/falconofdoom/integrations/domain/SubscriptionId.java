package com.falconofdoom.integrations.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class SubscriptionId {
    private String value;

    public static SubscriptionId create() {
        return new SubscriptionId(UUID.randomUUID().toString());
    }
    public static SubscriptionId of(String value) {
        return new SubscriptionId(value);
    }

    @Override
    public String toString() {
        return value;
    }

    private SubscriptionId(String value) {
        this.value = value;
    }

}
