package org.demo.notifier.internal.channels.resolver;

import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.service.ChannelResolver;
import org.demo.notifier.internal.service.NotificationChannel;

import java.util.Map;

public class DefaultChannelResolver implements ChannelResolver {

    private final Map<ChannelType, NotificationChannel> notificationChannel;

    public DefaultChannelResolver(Map<ChannelType, NotificationChannel> notificationChannel) {
        this.notificationChannel = Map.copyOf(notificationChannel);
    }

    @Override
    public NotificationChannel resolve(ChannelType channelType) {
        if(channelType == null){
            throw new RuntimeException("Channel type could not be null");
        }

        NotificationChannel result  = notificationChannel.get(channelType);
        if(result == null){
            throw new RuntimeException("Channel not found");
        }
        return result;
    }
}
