package org.demo.notifier.common.utils;

import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.DestinationDto;
import org.demo.notifier.internal.model.dto.domain.DigitalDeliveryPoliciesDto;
import org.demo.notifier.internal.model.dto.domain.NotificationContentDto;
import org.demo.notifier.internal.model.dto.domain.NotificationDto;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.model.enums.PriorityType;

import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static DeliveryRequestRecord createGenericRequest(List<String> toList, int retries, List<Object> attachments, String from, PriorityType priority, ChannelType channelType) {
        NotificationContentDto notificationContentDto = new NotificationContentDto.Builder()
                .setHeader("title")
                .setBody("test message")
                .setAttachments(attachments)
                .build();

        NotificationDto notificationDto = new NotificationDto.Builder()
                .setId(UUID.randomUUID().toString())
                .setNotificationContent(notificationContentDto)
                .build();

        DestinationDto destinationDto = new DestinationDto.Builder()
                .to(toList)
                .from(from)
                .build();

        DigitalDeliveryPoliciesDto deliveryPoliciesDto = new DigitalDeliveryPoliciesDto.Builder()
                .setPriority(priority)
                .setRetriesNumber(retries)
                .build();
        return new DeliveryRequestRecord(notificationDto, destinationDto, deliveryPoliciesDto, channelType);
    }

    public static ChannelConfiguration configureChannel(boolean allowRetries, boolean allowAttachments) {
        return new ChannelConfiguration.Builder()
                .allowAttachments(allowAttachments)
                .allowRetries(allowRetries)
                .build();
    }
}
