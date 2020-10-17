package com.falconofdoom.integrations.application.service;

import com.falconofdoom.integrations.domain.IntegrationType;
import com.falconofdoom.integrations.domain.Subscription;
import com.falconofdoom.integrations.domain.SubscriptionId;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class SubscriptionQueryService {

    public Multi<Subscription> findAllSubscriptions() {
        return Multi.createFrom().items(subscriptions().toArray(new Subscription[0]));
    }

    public Uni<Subscription> findById(SubscriptionId subscriptionId) {
        Optional<Subscription> subscription = subscriptions().stream().filter(s ->
                s.hasSameIdAs(subscriptionId)).findFirst();
        return Uni.createFrom().optional(subscription);
    }

    private List<Subscription> subscriptions() {
        return Arrays.asList(new Subscription(SubscriptionId.of("sub-1"), "johndoe-123",
                        "contactrequest", IntegrationType.INBOUND_WEBHOOK, "xsecretx1", null),
                new Subscription(SubscriptionId.of("sub-2"), "johndoe-123",
                        "homevaluation", IntegrationType.INBOUND_WEBHOOK, "xsecretx2", null));
    }
}
