package org.demo.notifier.internal.model.enums;

public enum NotificationStatus {
    SUCCESS(true), FAILURE(false);

    public final Boolean status;

    NotificationStatus(Boolean channelTypeValue) {
        this.status = channelTypeValue;
    }

    public Boolean getStatus() {
        return status;
    }
}
