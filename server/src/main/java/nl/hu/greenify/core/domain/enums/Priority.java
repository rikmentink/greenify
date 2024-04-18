package nl.hu.greenify.core.domain.enums;

import lombok.Getter;

public enum Priority {
    TOP_PRIORITY(2),
    PRIORITY(1.67),
    LITTLE_PRIORITY(1.33),
    NO_PRIORITY(1),
    PENDING(0);

    @Getter
    private final double value;

    Priority(double value) {
        this.value = value;
    }
}
