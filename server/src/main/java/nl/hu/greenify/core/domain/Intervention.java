package nl.hu.greenify.core.domain;

import jakarta.persistence.*;
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
    private List<Phase> phases = new ArrayList<>();

    @ManyToOne
    private Person admin;

    @ManyToMany
    private List<Person> participants = new ArrayList<>();

    public Intervention(String name, String description, Person admin) {
        if (name == null) {
            throw new IllegalArgumentException("Intervention should have a name");
        }

        if(admin == null) {
            throw new IllegalArgumentException("Intervention should have an admin");
        }

        this.name = name;
        this.description = description;
        this.admin = admin;
    }

    protected Intervention() {
    }

    public void addPhase(Phase phase) {
        if (phase == null) {
            throw new IllegalArgumentException("An intervention should not be able to add an invalid phase");
        }
        this.phases.add(phase);
    }

    public void addParticipant(Person person) {
        this.participants.add(person);
    }
}
