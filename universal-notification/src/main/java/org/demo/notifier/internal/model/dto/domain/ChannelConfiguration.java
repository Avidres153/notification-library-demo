package org.demo.notifier.internal.model.dto.domain;

import java.util.Objects;

public final class ChannelConfiguration {
    private final Boolean allowRetries;
    private final Boolean allowAttachments;

    private ChannelConfiguration(Builder builder) {
        this.allowRetries = Objects.requireNonNullElse(builder.allowRetries, Boolean.FALSE);
        this.allowAttachments = Objects.requireNonNullElse(builder.allowAttachments, Boolean.FALSE);
    }

    public static class Builder {
        private Boolean allowRetries;
        private Boolean allowAttachments;

        public Builder allowRetries(Boolean allowRetries) {
            this.allowRetries = allowRetries;
            return this;
        }

        public Builder allowAttachments(Boolean allowAttachments) {
            this.allowAttachments = allowAttachments;
            return this;
        }

        public ChannelConfiguration build(){return new ChannelConfiguration(this);}
    }

    public Boolean allowRetries() {
        return allowRetries;
    }
    
    public Boolean allowAttachments() {
        return allowAttachments;
    }
}
