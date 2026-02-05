package org.demo.notifier.api.impl;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;
import org.demo.notifier.api.NotificationServiceClient;
import org.demo.notifier.internal.service.NotificationDispatcher;

public final class NotificationClientImpl implements NotificationServiceClient {

    private final NotificationDispatcher dispatcher;

    public NotificationClientImpl(NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public NotificationResultDto send(DeliveryRequestRecord requestRecord) {
        return dispatcher.dispatch(requestRecord);
    }
}
