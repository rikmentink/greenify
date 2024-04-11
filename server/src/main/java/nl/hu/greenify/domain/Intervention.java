package nl.hu.greenify.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import nl.hu.greenify.domain.enums.PhaseName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@Entity
public class Intervention { // Add relationship with Phase
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String description;

    @OneToMany
    private List<Phase> phases;

    public Intervention(String name, String description) {
        if(name == null) {
            throw new IllegalArgumentException("Intervention should have a name and description");
        }
        this.name = name;
        this.description = description;
        this.phases = new ArrayList<>();
    }

    public Intervention() {
    }

    public void addPhase(PhaseName phaseName) { //Intervention side
        this.phases.add(new Phase(phaseName, this));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Intervention that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
