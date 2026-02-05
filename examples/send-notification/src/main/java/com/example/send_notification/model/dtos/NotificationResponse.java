package com.example.send_notification.model.dtos;

public record NotificationResponse(
        String notificationId,
        Boolean status,
        String message
) {}
