package com.example.send_notification.configuration.client;

import com.example.send_notification.configuration.channels.EmailChannelConfiguration;
import org.demo.notifier.api.NotificationServiceClient;
import org.demo.notifier.api.factory.NotificationClientFactory;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ClientNotification {

    @Autowired
    private EmailChannelConfiguration emailChannel;

    public NotificationServiceClient getClient() {
        return
                NotificationClientFactory.create(
                        Map.of(ChannelType.EMAIL, emailChannel.getEmailChannel())
                );
    }

}
