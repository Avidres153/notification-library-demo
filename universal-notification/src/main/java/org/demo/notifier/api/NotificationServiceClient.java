package org.demo.notifier.api;

import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;

public interface NotificationServiceClient {

    NotificationResultDto send(DeliveryRequestRecord requestRecord);

}
