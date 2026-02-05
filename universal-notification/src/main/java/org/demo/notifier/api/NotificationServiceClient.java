package org.demo.notifier.api;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;

public interface NotificationServiceClient {

    NotificationResultDto send(DeliveryRequestRecord requestRecord);

}
