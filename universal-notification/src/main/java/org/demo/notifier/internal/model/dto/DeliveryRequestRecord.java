package org.demo.notifier.internal.model.dto;

import org.demo.notifier.internal.model.enums.ChannelType;

public record DeliveryRequestRecord(NotificationDto notification, DestinationDto destination, DigitalDeliveryPoliciesDto digitalDeliveryPolicies, ChannelType channelType){
}
