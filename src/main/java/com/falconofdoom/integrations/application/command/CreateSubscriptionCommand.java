package com.falconofdoom.integrations.application.command;

import com.falconofdoom.integrations.domain.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateSubscriptionCommand {

    @NotEmpty
    private String subscriberId;
    @NotEmpty
    private String eventName;
    @NotEmpty
    private String sharedSecret;

    @NotNull
    private Context context;

    public Subscription toSubscription() {
        SubscriptionContext context = (this.context == null) ? null : this.context.toSubscriptionContext();
        return Subscription.builder().id(SubscriptionId.create()).subscriberId(this.subscriberId)
                .eventName(this.eventName).sharedSecret(this.sharedSecret)
                .type(this.context.type())
                .context(context).build();

    }


    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Context.Webhook.class, name = "INBOUND_WEBHOOK"),
    })
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public interface Context {
        SubscriptionContext toSubscriptionContext();
        IntegrationType type();

        @Data
        @JsonTypeName("INBOUND_WEBHOOK")
        class Webhook implements Context {
            private String targetUrl;

            @Override
            public SubscriptionContext toSubscriptionContext() {
                return new WebhookSubscriptionContext(targetUrl);
            }

            @Override
            public IntegrationType type() {
                return IntegrationType.INBOUND_WEBHOOK;
            }
        }
    }
}
