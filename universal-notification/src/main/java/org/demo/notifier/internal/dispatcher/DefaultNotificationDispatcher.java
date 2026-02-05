package org.demo.notifier.internal.dispatcher;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;
import org.demo.notifier.internal.service.ChannelResolver;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.NotificationDispatcher;

import java.util.UUID;

public class DefaultNotificationDispatcher implements NotificationDispatcher {

    private final ChannelResolver channelResolver;

    public DefaultNotificationDispatcher(ChannelResolver channelResolver) {
        this.channelResolver = channelResolver;
    }


    @Override
    public NotificationResultDto dispatch(DeliveryRequestRecord requestRecord) {
        try {
            NotificationChannel channel = channelResolver.resolve(requestRecord.channelType());
            return sendNotificationWithRetries(requestRecord, channel);
        } catch (Exception e) {
            return NotificationResultDto.failure(
                    UUID.randomUUID().toString(),
                    e.getMessage()
            );
        }

    }

    private NotificationResultDto sendNotificationWithRetries(DeliveryRequestRecord requestRecord, NotificationChannel channel) {
        int counter = 0;
        int retriesNumber = requestRecord.digitalDeliveryPolicies().retriesNumber();
        NotificationResultDto notificationResult = null;
        do {
            System.out.println("Sending Notification with retries. Retry -> " + (counter + 1));
            notificationResult = channel.send(requestRecord);

            if (notificationResult.status()) {
                return notificationResult;
            }
            counter++;
        } while (counter < retriesNumber);

        return NotificationResultDto.failure(UUID.randomUUID().toString(), "The notification could not be sent...number of retries exceeded.".concat(" || Details: ").concat(notificationResult.message()));
    }
}
