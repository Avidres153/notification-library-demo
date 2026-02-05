package org.demo.notifier.api.factory;

import org.demo.notifier.api.NotificationServiceClient;
import org.demo.notifier.api.impl.NotificationClientImpl;
import org.demo.notifier.internal.channels.resolver.DefaultChannelResolver;
import org.demo.notifier.internal.dispatcher.DefaultNotificationDispatcher;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.NotificationDispatcher;

import java.util.Map;

public class NotificationClientFactory {

    private NotificationClientFactory() {
    }

    public static NotificationServiceClient create(
            Map<ChannelType, NotificationChannel> channels
    ) {

        if (channels == null || channels.isEmpty()) {
            throw new IllegalArgumentException("Channels must not be empty");
        }

        NotificationDispatcher dispatcher =
                new DefaultNotificationDispatcher(
                        new DefaultChannelResolver(channels)
                );

        return new NotificationClientImpl(dispatcher);
    }

}
