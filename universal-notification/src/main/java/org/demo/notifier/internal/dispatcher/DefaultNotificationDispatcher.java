package org.demo.notifier.internal.dispatcher;

import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.service.ChannelResolver;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.NotificationDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DefaultNotificationDispatcher implements NotificationDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(DefaultNotificationDispatcher.class);

    private final ChannelResolver channelResolver;

    public DefaultNotificationDispatcher(ChannelResolver channelResolver) {
        this.channelResolver = channelResolver;
    }


    @Override
    public NotificationResultDto dispatch(DeliveryRequestRecord requestRecord) {
        try {
            NotificationChannel channel = channelResolver.resolve(requestRecord.channelType());
            if (requestRecord.digitalDeliveryPolicies().retriesNumber() == 0) {
                if(logger.isInfoEnabled()){
                    logger.info(" ---> Trying to send notification");
                }
                return channel.send(requestRecord);
            }

            return sendNotificationWithRetries(requestRecord, channel);
        } catch (Exception e) {
            logger.error(e.getMessage());
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
            if(logger.isInfoEnabled()){
                logger.info(" ---> Trying to send notification with retries ----> retry: {}", (counter + 1) );
            }
            notificationResult = channel.send(requestRecord);

            if (notificationResult.status()) {
                return notificationResult;
            }
            counter++;
        } while (counter < retriesNumber);

        return NotificationResultDto.failure(UUID.randomUUID().toString(), "The notification could not be sent...number of retries exceeded.".concat(" || Details: ").concat(notificationResult.message()));
    }
}
