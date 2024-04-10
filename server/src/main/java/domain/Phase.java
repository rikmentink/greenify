package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Phase {
    private Long id;
    private String name;
    private Intervention intervention;

    public Phase(Long id, String name, Intervention intervention) {
        this.id = id;
        this.name = name;
        this.intervention = intervention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phase phase)) return false;
        return Objects.equals(intervention, phase.intervention);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intervention);
    }

    @Override
    public String toString() {
        return "Phase{" +
                "intervention=" + intervention +
                '}';
    }
}
