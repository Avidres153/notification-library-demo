package org.demo.notifier.internal.model.enums;

public enum PriorityType {
    LOW(1), MEDIUM(2), HIGH(3);

    private final int priorityValue;

    PriorityType(int priorityValue) {
        this.priorityValue = priorityValue;
    }

    public int getPriorityValue() {
        return priorityValue;
    }
}
