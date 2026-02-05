package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.internal.model.dto.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.EmailPayloadDto;
import org.demo.notifier.internal.service.EmailProvider;
import org.demo.notifier.internal.service.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailChannel implements NotificationChannel {

    private static final Logger logger = LoggerFactory.getLogger(EmailChannel.class);
    private final ChannelConfiguration channelConfiguration;
    private final EmailProvider provider;

    public EmailChannel(ChannelConfiguration channelConfiguration, EmailProvider provider) {
        this.channelConfiguration = channelConfiguration;
        this.provider = provider;
    }

    @Override
    public NotificationResultDto send(DeliveryRequestRecord requestRecord) {
        try {

            validateRequest(requestRecord);

            EmailPayloadDto emailPayload = EmailPayloadDto.fromRequestRecord(requestRecord);

            return provider.send(emailPayload);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void validateRequest(DeliveryRequestRecord requestRecord) {

        if (logger.isInfoEnabled()) {
            logger.info(" ---> Validating request");
        }

        if (!channelConfiguration.allowAttachments() && !requestRecord.notification().notificationContent().attachments().isEmpty()) {
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
