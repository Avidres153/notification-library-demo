package org.demo.notifier.internal.service;

import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.SmsPayloadDto;

public interface SmsProvider {
    NotificationResultDto send(SmsPayloadDto payload);
}
