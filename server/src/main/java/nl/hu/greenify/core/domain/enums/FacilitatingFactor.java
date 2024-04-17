package nl.hu.greenify.core.domain.enums;

import lombok.Getter;

public enum FacilitatingFactor {
    TOTALLY_DISAGREE(1),
    DISAGREE(2),
    NEUTRAL(3),
    AGREE(4),
    TOTALLY_AGREE(5),
    I_DONT_KNOW(0),
    PENDING(0);

    @Getter
    private final int value;

    FacilitatingFactor(int value) {
        this.value = value;
    }
}
