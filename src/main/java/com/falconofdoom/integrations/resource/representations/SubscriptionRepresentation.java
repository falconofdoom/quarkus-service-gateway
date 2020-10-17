package com.falconofdoom.integrations.resource.representations;

import com.falconofdoom.integrations.domain.Subscription;
import com.falconofdoom.integrations.domain.SubscriptionContext;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubscriptionRepresentation {
    private String id;
    private String subscriberId;
    private String eventName;
    private String type;
    private String sharedSecret;
    private SubscriptionContext context;

    public static SubscriptionRepresentation of(Subscription subscription) {
        return new SubscriptionRepresentation(
                subscription.getId().getValue(), subscription.getSubscriberId(), subscription.getEventName(),
                subscription.getType().toString(), subscription.getSharedSecret(),
                subscription.getContext());
    }
}
