package org.kata.dto.enums;

public enum NotificationType {

    PUSH ("push"),
    VERIFICATION_CODE ("verification code");

    private final String type;

    NotificationType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
