package org.demo.notifier.internal.service;

import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;

public interface NotificationChannel {

    NotificationResultDto send(DeliveryRequestRecord requestRecord);
}
