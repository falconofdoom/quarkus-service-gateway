package com.falconofdoom.integrations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class WebhookSubscriptionContext implements SubscriptionContext {
    private String targetUrl;
}
