package org.demo.notifier.internal.service;

import org.demo.notifier.internal.model.enums.ChannelType;

public interface ChannelResolver {

    NotificationChannel resolve(ChannelType channelType);

}
