package org.demo.notifier.internal.service;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;

public interface NotificationChannel {

    NotificationResultDto send(DeliveryRequestRecord requestRecord);
}
