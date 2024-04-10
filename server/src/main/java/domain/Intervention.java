package domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
public class Intervention { // Add relationship with Phase
    @GeneratedValue
    @Id
    private Long id;
    private final String name;
    private final String description;
    private List<Phase> phases;

    public Intervention(String name, String description) {
        if(name == null) {
            throw new IllegalArgumentException("Intervention should have a name and description");
        }
        this.name = name;
        this.description = description;
        this.phases = new ArrayList<>();
    }

    public void addPhase(String name) {
        if(this.phases.size() >= 3) {
            throw new IllegalArgumentException("Intervention can only have 3 phases");
        }
        for(Phase p : this.phases) {
            if(p.getName().equals(name)) {
                throw new IllegalArgumentException("Phase with name " + name + " already exists");
            }
        }
        this.phases.add(new Phase(name, this));
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
