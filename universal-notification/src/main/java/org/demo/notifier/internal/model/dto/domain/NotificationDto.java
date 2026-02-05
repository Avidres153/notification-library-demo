package org.demo.notifier.internal.model.dto.domain;

public final class NotificationDto {

    private final String id;
    private final NotificationContentDto notificationContent;

    public NotificationDto(Builder builder) {
        this.id = builder.id;
        this.notificationContent = builder.notificationContent;
    }

    public static class Builder{
        private String id;
        private NotificationContentDto notificationContent;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNotificationContent(NotificationContentDto notificationContent) {
            this.notificationContent = notificationContent;
            return this;
        }

        public NotificationDto build() {
            return new NotificationDto(this);
        }
    }

    public String id() {
        return id;
    }

    public NotificationContentDto notificationContent() {
        return notificationContent;
    }
}
