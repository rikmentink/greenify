package nl.hu.greenify.core.domain.enums;

public enum PhaseName { // default values for the phases of the project
    DEVELOPMENT("Ontwikkeling"),
    EXECUTION("Uitvoering"),
    EVALUATION("Evaluatie");

    private String value;

    private PhaseName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
