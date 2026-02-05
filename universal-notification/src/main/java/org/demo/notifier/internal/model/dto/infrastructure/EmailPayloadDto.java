package org.demo.notifier.internal.model.dto.infrastructure;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;

import java.util.List;

public final class EmailPayloadDto {

    private final List<String> to;
    private final String from;
    private final List<String> copyTo;
    private final List<String> hiddenCopyTo;
    private final String subject;
    private final String body;
    private final List<Object> attachments;

    public EmailPayloadDto(Builder builder) {
        this.to = builder.to;
                this.from = builder.from;
        this.copyTo = builder.copyTo;
                this.hiddenCopyTo = builder.hiddenCopyTo;
        this.subject = builder.subject;
                this.body = builder.body;
        this.attachments = builder.attachments;
    }

    public static class Builder{
        private List<String> to;
        private String from;
        private List<String> copyTo;
        private List<String> hiddenCopyTo;
        private String subject;
        private String body;
        private List<Object> attachments;

        public Builder setTo(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setCopyTo(List<String> copyTo) {
            this.copyTo = copyTo;
            return this;
        }

        public Builder setHiddenCopyTo(List<String> hiddenCopyTo) {
            this.hiddenCopyTo = hiddenCopyTo;
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

        public Builder setAttachments(List<Object> attachments) {
            this.attachments = attachments;
            return this;
        }

        public EmailPayloadDto build(){return new EmailPayloadDto(this);}
    }

    public static EmailPayloadDto fromRequestRecord(DeliveryRequestRecord requestRecord) {
        return new EmailPayloadDto.Builder()
                .setTo(requestRecord.destination().to())
                .setFrom(requestRecord.destination().from())
                .setCopyTo(requestRecord.destination().hiddenCopyTo())
                .setHiddenCopyTo(requestRecord.destination().hiddenCopyTo())
                .setSubject(requestRecord.notification().notificationContent().header())
                .setBody(requestRecord.notification().notificationContent().body())
                .setAttachments(requestRecord.notification().notificationContent().attachments())
                .build();
    }
}
