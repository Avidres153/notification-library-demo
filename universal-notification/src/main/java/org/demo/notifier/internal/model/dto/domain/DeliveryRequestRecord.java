package org.demo.notifier.internal.model.dto.domain;

import org.demo.notifier.internal.model.enums.ChannelType;

public record DeliveryRequestRecord(NotificationDto notification, DestinationDto destination, DigitalDeliveryPoliciesDto digitalDeliveryPolicies, ChannelType channelType){
}
