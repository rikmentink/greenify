package nl.hu.greenify.core.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @ManyToOne
    private Person admin;

    @OneToMany
    private List<Phase> phases = new ArrayList<>();

    @ManyToMany
    private List<Person> participants = new ArrayList<>();

    protected Intervention() {
    }

    private Intervention(String name, String description, Person admin, List<Phase> phases, List<Person> participants) {
        this.name = name;
        this.description = description;
        this.admin = admin;
        this.phases = phases;
        this.participants = participants;
    }

    public Intervention(Long id, String name, String description, Person admin, List<Phase> phases,
            List<Person> participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.admin = admin;
        this.phases = phases;
        this.participants = participants;
    }

    public Phase getCurrentPhase() {
        if(this.phases.isEmpty()) {
            return null;
        }
        return this.phases.get(this.phases.size() - 1);
    }

    public List<Person> removeParticipant(Person person) {
        if(person == null) {
            throw new IllegalArgumentException("Person should not be null");
        }

        this.participants.remove(person);

        return this.participants;
    }

    public List<Survey> getSurveysOfPersonInCurrentPhase(Person person) {
        List<Survey> surveys = new ArrayList<>();

        if(person == null) {
            throw new IllegalArgumentException("Person should not be null");
        }

        if(this.getCurrentPhase() == null) {
            return new ArrayList<>();
        }

        for(Survey survey : person.getSurveys()) {
            if(survey.getPhase().equals(this.getCurrentPhase())) {
                surveys.add(survey);
            }
        }

        return surveys;
    }

    public static Intervention createIntervention(String name, String description, Person admin) {
        if (name == null) {
            throw new IllegalArgumentException("Intervention should have a name");
        }

        if(admin == null) {
            throw new IllegalArgumentException("Intervention should have an admin");
        }

        List<Person> participants = new ArrayList<>();
        participants.add(admin);
        return new Intervention(name, description, admin, new ArrayList<>(), participants);
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
