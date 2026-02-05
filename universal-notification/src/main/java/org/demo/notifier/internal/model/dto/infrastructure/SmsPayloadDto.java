package org.demo.notifier.internal.model.dto.infrastructure;

import org.demo.notifier.internal.model.dto.DeliveryRequestRecord;

import java.util.List;
import java.util.Objects;

public final class SmsPayloadDto {

    private final List<String> to;
    private final String from;
    private final String body;

    public SmsPayloadDto(Builder builder) {
        this.to = Objects.requireNonNull(builder.to);
        this.from = Objects.requireNonNull(builder.from);
        this.body = Objects.requireNonNull(builder.body);
    }

    public static SmsPayloadDto fromRequestRecord(DeliveryRequestRecord requestRecord) {
        return new SmsPayloadDto.Builder()
                .setFrom(requestRecord.destination().from())
                .setTo(requestRecord.destination().to())
                .setBody(requestRecord.notification().notificationContent().body())
                .build();
    }

    public static class Builder {
        private List<String> to;
        private String from;
        private String body;

        public Builder setTo(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public SmsPayloadDto build() {
            return new SmsPayloadDto(this);
        }
    }
}
