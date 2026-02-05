package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.PushNotificationPayload;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.PushNotificationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushNotificationChannel implements NotificationChannel {

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationChannel.class);
    private final ChannelConfiguration channelConfiguration;
    private final PushNotificationProvider provider;

    public PushNotificationChannel(ChannelConfiguration channelConfiguration, PushNotificationProvider provider) {
        this.channelConfiguration = channelConfiguration;
        this.provider = provider;
    }

    @Override
    public NotificationResultDto send(DeliveryRequestRecord requestRecord) {
        try {
            validateRequest(requestRecord);

            PushNotificationPayload pushNotificationPayload = PushNotificationPayload.fromRequestRecord(requestRecord);

            return provider.send(pushNotificationPayload);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private void validateRequest(DeliveryRequestRecord requestRecord) {
        if (logger.isInfoEnabled()) {
            logger.info(" ---> Validating request");
        }

        if (channelConfiguration.allowAttachments() || !requestRecord.notification().notificationContent().attachments().isEmpty()) {
            throw new RuntimeException("This channel not supports attachments");
        }

        if (channelConfiguration.allowRetries() && requestRecord.digitalDeliveryPolicies().retriesNumber() <= 0) {
            throw new RuntimeException("Retries number must be greater than 0");
        }

        if (!channelConfiguration.allowRetries() && requestRecord.digitalDeliveryPolicies().retriesNumber() > 0) {
            throw new RuntimeException("This channel not supports retries");
        }
    }
}
