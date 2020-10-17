package com.falconofdoom.integrations.application.service;

import com.falconofdoom.integrations.application.command.CreateSubscriptionCommand;
import com.falconofdoom.integrations.domain.Subscription;
import com.falconofdoom.integrations.domain.SubscriptionId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubscriptionApplicationService {
    public Subscription createSubscription(CreateSubscriptionCommand command) {
        Subscription result = command.toSubscription();
        return result;
    }

    private SubscriptionId generateId() {
        return SubscriptionId.create();
    }
}
