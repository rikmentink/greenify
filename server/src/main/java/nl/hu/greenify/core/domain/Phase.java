package nl.hu.greenify.core.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.hu.greenify.core.domain.enums.PhaseName;

@Getter
@Entity
@EqualsAndHashCode
@ToString
public class Phase {
    @GeneratedValue
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private PhaseName name;

    @OneToMany(mappedBy="phase", cascade=CascadeType.PERSIST)
    private List<Survey> surveys = new ArrayList<>();

    protected Phase() {
    }

    private Phase(PhaseName name) {
        this.name = name;
    }

    public Phase(Long id, PhaseName name) {
        this.id = id;
        this.name = name;
    }

    public static Phase createPhase(PhaseName name) {
        if(name == null) {
            throw new IllegalArgumentException("Phase should have a name");
        }
        
        return new Phase(name);
    }

    public void addSurvey(Survey survey) {
        this.surveys.add(survey);
    }

    public Survey getSurveyOfPerson(Person person) {
        return this.surveys.stream()
            .filter(survey -> survey.getRespondent().equals(person))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Person should have a survey in this phase"));
    }
}
