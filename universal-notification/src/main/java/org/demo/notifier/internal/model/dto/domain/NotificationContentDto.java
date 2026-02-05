package org.demo.notifier.internal.model.dto.domain;

import java.util.List;

public final class NotificationContentDto {

    private final String header;
    private final String body;
    private final List<Object> attachments;


    public NotificationContentDto(Builder builder) {
        this.header = builder.header;
        this.body = builder.body;
        this.attachments = builder.attachments;
    }

    public static class Builder{
        private String header;
        private String body;
        private List<Object> attachments;

        public Builder setHeader(String header) {
            this.header = header;
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

        public NotificationContentDto build() {
            return new NotificationContentDto(this);
        }
    }

    public String header() {
        return header;
    }

    public String body() {
        return body;
    }

    public List<Object> attachments() {
        return attachments;
    }
}
