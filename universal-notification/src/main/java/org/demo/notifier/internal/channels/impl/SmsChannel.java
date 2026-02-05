package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.internal.model.dto.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.SmsPayloadDto;
import org.demo.notifier.internal.service.NotificationChannel;
import org.demo.notifier.internal.service.SmsProvider;

public class SmsChannel implements NotificationChannel {

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
            throw new RuntimeException(e);
        }

    }

    private void validateRequest(DeliveryRequestRecord requestRecord) {
        if(channelConfiguration.allowAttachments() || !requestRecord.notification().notificationContent().attachments().isEmpty()){
            throw new RuntimeException("This channel not supports attachments");
        }

        if (!channelConfiguration.allowRetries() && requestRecord.digitalDeliveryPolicies().retriesNumber() > 0) {
            throw new RuntimeException("This channel not supports retries");
        }

        if(channelConfiguration.allowRetries() && requestRecord.digitalDeliveryPolicies().retriesNumber() <= 0){
            throw new RuntimeException("Retries number must be greater than 0");
        }
    }
}
