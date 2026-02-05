package org.demo.notifier.internal.model.enums;

public enum ChannelType {
    EMAIL("email"), SMS("sms"), PUSH_NOTIFICATION("push");

    public final String status;

    ChannelType(String channelTypeValue) {
        this.status = channelTypeValue;
    }

    public String getStatus() {
        return status;
    }
}
