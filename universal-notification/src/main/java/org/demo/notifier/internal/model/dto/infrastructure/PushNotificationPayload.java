package org.demo.notifier.internal.model.dto.infrastructure;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;

import java.util.List;

public final class PushNotificationPayload {

    private final String from;
    private final String subject;
    private final String body;

    public PushNotificationPayload(Builder builder){
        this.from = builder.from;
        this.subject = builder.from;
        this.body = builder.from;
    }

    public static PushNotificationPayload fromRequestRecord(DeliveryRequestRecord requestRecord) {
        return new PushNotificationPayload.Builder()
                .setFrom(requestRecord.destination().from())
                .setSubject(requestRecord.notification().notificationContent().header())
                .setBody(requestRecord.notification().notificationContent().body())
                .build();
    }

    public static class Builder{
        private String from;
        private String subject;
        private String body;

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public PushNotificationPayload build(){return new PushNotificationPayload(this);}
    }
}
