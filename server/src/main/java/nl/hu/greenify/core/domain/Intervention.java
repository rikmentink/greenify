package nl.hu.greenify.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.enums.PhaseName;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@EqualsAndHashCode
@ToString
public class Intervention {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String description;

    @OneToMany
    private List<Phase> phases;

    public Intervention(String name, String description) {
        if (name == null) {
            throw new IllegalArgumentException("Intervention should have a name and description");
        }
        this.name = name;
        this.description = description;
        this.phases = new ArrayList<>();
    }

    protected Intervention() {
    }

    public void addPhase(PhaseName phaseName) { //Intervention side
        for (Phase p : this.phases) {
            if (p.getName().equals(phaseName)) {
                throw new IllegalArgumentException("Phase with name " + phaseName + " already exists");
            }
        }
        this.phases.add(new Phase(phaseName));
    }
}
