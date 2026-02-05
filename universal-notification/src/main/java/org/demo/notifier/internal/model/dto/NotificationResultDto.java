package org.demo.notifier.internal.model.dto;

import org.demo.notifier.internal.model.enums.NotificationStatus;

public final class NotificationResultDto {

    private final Boolean status;
    private final String externalId;
    private final String message;

    private NotificationResultDto(Builder builder) {
        this.status = builder.status;
        this.externalId = builder.externalId;
        this.message = builder.message;
    }

    public static NotificationResultDto success(String externalId) {
        return new Builder().status(NotificationStatus.SUCCESS.getStatus()).externalId(externalId).build();
    }

    public static NotificationResultDto failure(String externalId, String message){
        return new Builder().status(NotificationStatus.FAILURE.getStatus()).externalId(externalId).message(message).build();
    }

    public static class Builder{
        private Boolean status;
        private String externalId;
        private String message;

        public Builder status(Boolean status) {
            this.status = status;
            return this;
        }

        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public NotificationResultDto build(){return new NotificationResultDto(this);}
    }

    public Boolean status() {
        return status;
    }

    public String externalId() {
        return externalId;
    }

    public String message() {
        return message;
    }
}
