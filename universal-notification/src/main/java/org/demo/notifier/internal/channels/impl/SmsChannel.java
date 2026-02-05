package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.SmsPayloadDto;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.SmsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsChannel implements NotificationChannel {

    private static final Logger logger = LoggerFactory.getLogger(SmsChannel.class);
    private final ChannelConfiguration channelConfiguration;
    private final SmsProvider smsProvider;

    public SmsChannel(ChannelConfiguration channelConfiguration, SmsProvider smsProvider) {
        this.channelConfiguration = channelConfiguration;
        this.smsProvider = smsProvider;
    }

    @Override
    public NotificationResultDto send(DeliveryRequestRecord requestRecord) {

        try {
            validateRequest(requestRecord);
            SmsPayloadDto smsPayload = SmsPayloadDto.fromRequestRecord(requestRecord);
            return smsProvider.send(smsPayload);
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
