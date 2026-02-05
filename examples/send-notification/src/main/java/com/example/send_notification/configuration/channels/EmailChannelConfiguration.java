package com.example.send_notification.configuration.channels;

import com.example.send_notification.configuration.providers.ExampleEmailProvider;
import org.demo.notifier.internal.channels.impl.EmailChannel;
import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.service.NotificationChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailChannelConfiguration {

    @Autowired
    private ExampleEmailProvider exampleEmailProvider;

    public NotificationChannel getEmailChannel(){
        ChannelConfiguration emailConfig =
                new ChannelConfiguration.Builder()
                        .allowAttachments(true)
                        .allowRetries(true)
                        .build();

        return new EmailChannel(emailConfig, exampleEmailProvider);
    }

}
