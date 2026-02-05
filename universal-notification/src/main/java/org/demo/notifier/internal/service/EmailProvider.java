package org.demo.notifier.internal.service;

import org.demo.notifier.internal.model.dto.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.EmailPayloadDto;

public interface EmailProvider {

    NotificationResultDto send(EmailPayloadDto emailPayloadDto);
}
