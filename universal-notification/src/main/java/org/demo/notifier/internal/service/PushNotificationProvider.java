package org.demo.notifier.internal.service;

import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.PushNotificationPayload;

public interface PushNotificationProvider{
    NotificationResultDto send(PushNotificationPayload payload);
}
