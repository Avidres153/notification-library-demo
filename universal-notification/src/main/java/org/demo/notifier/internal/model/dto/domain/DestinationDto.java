package org.demo.notifier.internal.model.dto.domain;

import java.util.List;
import java.util.Objects;

public final class DestinationDto {

    private final List<String> to;
    private final String from;
    private final List<String> copyTo;
    private final List<String> hiddenCopyTo;

    private DestinationDto(Builder builder) {
        this.to = Objects.requireNonNull(builder.to);
        this.from = builder.from;
        this.copyTo = builder.copyTo;
        this.hiddenCopyTo = builder.hiddenCopyTo;
    }

    public static class Builder {
        private List<String> to;
        private String from;
        private List<String> copyTo;
        private List<String> hiddenCopyTo;

        public Builder to(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder copyTo(List<String> copyTo) {
            this.copyTo = copyTo;
            return this;
        }

        public Builder hiddenCopyTo(List<String> hiddenCopyTo) {
            this.hiddenCopyTo = hiddenCopyTo;
            return this;
        }

        public DestinationDto build() {
            return new DestinationDto(this);
        }

    }

    public List<String> to() {
        return to;
    }

    public String from() {
        return from;
    }

    public List<String> copyTo() {
        return copyTo;
    }

    public List<String> hiddenCopyTo() {
        return hiddenCopyTo;
    }
}
