package org.demo.notifier.internal.channels.factory;

import org.demo.notifier.internal.channels.resolver.DefaultChannelResolver;
import org.demo.notifier.internal.dispatcher.DefaultNotificationDispatcher;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.NotificationDispatcher;

import java.util.Map;

public final class ChannelDispatcherFactory {

    private ChannelDispatcherFactory() {
    }

    public static NotificationDispatcher create(
            Map<ChannelType, NotificationChannel> channels
    ) {
        org.demo.notifier.internal.service.ChannelResolver resolver =
                new DefaultChannelResolver(channels);

        return new DefaultNotificationDispatcher(
                resolver
        );
    }
}
