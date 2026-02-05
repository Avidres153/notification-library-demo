package com.example.send_notification.service;

import com.example.send_notification.configuration.client.ClientNotification;
import com.example.send_notification.model.dtos.EmailRequest;
import com.example.send_notification.model.dtos.NotificationResponse;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.DestinationDto;
import org.demo.notifier.internal.model.dto.domain.DigitalDeliveryPoliciesDto;
import org.demo.notifier.internal.model.dto.domain.NotificationContentDto;
import org.demo.notifier.internal.model.dto.domain.NotificationDto;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.model.enums.PriorityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationExampleService {

    @Autowired
    private ClientNotification client;

    public NotificationResponse sendEmail(EmailRequest emailRequest) {

        DeliveryRequestRecord request = createGenericRequest(
                emailRequest.getSubject(),
                emailRequest.getBody(),
                emailRequest.getTo(),
                3,
                emailRequest.getAttachments(),
                emailRequest.getFrom(),
                PriorityType.LOW,
                ChannelType.EMAIL);


        NotificationResultDto result = client.getClient().send(request);

        return new NotificationResponse(result.externalId(), result.status(), result.message());

    }

    private DeliveryRequestRecord createGenericRequest(String header, String body, List<String> toList, int retries, List<Object> attachments, String from, PriorityType priority, ChannelType channelType) {
        NotificationContentDto notificationContentDto = new NotificationContentDto.Builder()
                .setHeader(header)
                .setBody(body)
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

}
