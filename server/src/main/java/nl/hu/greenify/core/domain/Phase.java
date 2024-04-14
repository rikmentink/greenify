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

@Getter
@Entity
@EqualsAndHashCode
@ToString
public class Phase {
    @GeneratedValue
    @Id
    private Long id;

    @Setter
    private PhaseName name;

    @OneToMany
    private List<Survey> surveys = new ArrayList<>();

    public Phase(PhaseName name) {
        if(name == null) {
            throw new IllegalArgumentException("Phase should have a name");
        }
        this.name = name;
    }

    protected Phase() {
    }

    public void addSurvey(Survey survey) {
        this.surveys.add(survey);
    }
}
