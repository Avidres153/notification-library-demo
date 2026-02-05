package org.demo.notifier.internal.dispatcher;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;
import org.demo.notifier.internal.service.ChannelResolver;
import org.demo.notifier.internal.service.NotificationDispatcher;

public class DefaultNotificationDispatcher implements NotificationDispatcher {

    private final ChannelResolver channelResolver;

    public DefaultNotificationDispatcher(ChannelResolver channelResolver) {
        this.channelResolver = channelResolver;
    }


    @Override
    public NotificationResultDto dispatch(DeliveryRequestRecord requestRecord) {
        try {
            return channelResolver.resolve(requestRecord.channelType()).send(requestRecord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
