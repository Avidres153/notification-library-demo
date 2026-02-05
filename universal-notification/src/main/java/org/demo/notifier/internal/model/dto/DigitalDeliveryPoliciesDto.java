package org.demo.notifier.internal.model.dto;

import org.demo.notifier.internal.model.enums.PriorityType;

import java.util.Objects;

public final class DigitalDeliveryPoliciesDto {
    private final Integer timeout;
    private final Integer retriesNumber;
    private final PriorityType priorityType;

    private DigitalDeliveryPoliciesDto(Builder builder) {
        this.timeout = Objects.requireNonNullElse(builder.timeout, 5000);
        this.retriesNumber = Objects.requireNonNullElse(builder.retriesNumber, 0);
        this.priorityType = Objects.requireNonNullElse(builder.priorityType, PriorityType.LOW);

    }

    public static class Builder{
        private Integer timeout;
        private Integer retriesNumber;
        private PriorityType priorityType;

        public Builder setTimeout(Integer timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setRetriesNumber(Integer retriesNumber) {
            this.retriesNumber = retriesNumber;
            return this;
        }

        public Builder setPriority(PriorityType priorityType) {
            this.priorityType = priorityType;
            return this;
        }

        public DigitalDeliveryPoliciesDto build(){return new DigitalDeliveryPoliciesDto(this);
            }
    }

    public Integer timeout() {
        return timeout;
    }

    public Integer retriesNumber() {
        return retriesNumber;
    }

    public PriorityType priorityType() {
        return priorityType;
    }
}
