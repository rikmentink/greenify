package nl.hu.greenify.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import nl.hu.greenify.domain.enums.PhaseName;

import java.util.Objects;

@Getter
@Setter
@Entity
public class Phase {
    @GeneratedValue
    @Id
    private Long id;
    private PhaseName name;

    @OneToOne
    private Intervention intervention;

    public Phase(PhaseName name, Intervention intervention) {
        if(name == null) {
            throw new IllegalArgumentException("Phase should have a name");
        }
        this.name = name;
        this.intervention = intervention;
    }

    public Phase() {
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
                "id=" + id +
                ", name=" + name +
                ", intervention=" + intervention +
                '}';
    }
}
