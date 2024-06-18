package nl.hu.greenify.core.domain.enums;

public enum PhaseName { // default values for the phases of the project
    INITIATION("Initiation"),
    PLANNING("Planning"),
    EXECUTION("Execution");

    private String value;

    private PhaseName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
