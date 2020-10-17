package com.falconofdoom.integrations.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Builder
public class Subscription {
    private SubscriptionId id;
    private String subscriberId;
    private String eventName;
    private IntegrationType type;
    @JsonIgnore
    private String sharedSecret;
    private SubscriptionContext context;

    public boolean hasSameIdAs(SubscriptionId other) {
        return Objects.equals(this.id, other);
    }
}