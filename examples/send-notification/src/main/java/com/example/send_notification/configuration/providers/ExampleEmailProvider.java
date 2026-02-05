package com.example.send_notification.configuration.providers;

import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.EmailPayloadDto;
import org.demo.notifier.internal.service.EmailProvider;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ExampleEmailProvider implements EmailProvider {

    @Override
    public NotificationResultDto send(EmailPayloadDto emailPayloadDto) {

        return NotificationResultDto.success(UUID.randomUUID().toString());

    }
}
