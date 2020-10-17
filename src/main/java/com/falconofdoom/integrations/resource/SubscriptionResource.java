package com.falconofdoom.integrations.resource;

import com.falconofdoom.integrations.application.command.CreateSubscriptionCommand;
import com.falconofdoom.integrations.application.service.SubscriptionApplicationService;
import com.falconofdoom.integrations.application.service.SubscriptionQueryService;
import com.falconofdoom.integrations.domain.SubscriptionId;
import com.falconofdoom.integrations.resource.representations.SubscriptionRepresentation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class SubscriptionResource {

    private SubscriptionQueryService subscriptionQueryService;
    private SubscriptionApplicationService subscriptionApplicationService;

    @Inject
    public SubscriptionResource(SubscriptionQueryService subscriptionQueryService,
                                SubscriptionApplicationService subscriptionApplicationService) {
        this.subscriptionQueryService = subscriptionQueryService;
        this.subscriptionApplicationService = subscriptionApplicationService;
    }

    @GET
    public Multi<SubscriptionRepresentation> listAll() {
        return subscriptionQueryService.findAllSubscriptions().map(
                SubscriptionRepresentation::of
        );
    }

    @GET
    @Path("/{id}")
    public Uni<SubscriptionRepresentation> showById(@PathParam("id") String id) {
        return subscriptionQueryService.findById(SubscriptionId.of(id))
                .onItem().transform(x -> SubscriptionRepresentation.of(x));
    }


    @POST
    public Uni<Response> create(@Valid CreateSubscriptionCommand createSubscriptionCommand, @Context UriInfo uriInfo) {
        System.out.println(createSubscriptionCommand);
        return Uni.createFrom().item(createSubscriptionCommand).onItem()
                .transformToUni(subscriptionOperation
                        -> Uni.createFrom()
                        .item(subscriptionApplicationService.createSubscription(createSubscriptionCommand)))
                .onItem().transformToUni(subscription ->
                        Uni.createFrom().item(SubscriptionRepresentation.of(subscription)))
                .onItem().transform(representation -> newSubscriptionResponse(representation, uriInfo));
    }

    private Response newSubscriptionResponse(SubscriptionRepresentation representation, UriInfo uriInfo) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(representation.getId());
        log.debug("New subscription created with URI " + builder.build().toString());
        return Response.created(builder.build()).entity(representation).build();
    }

}
