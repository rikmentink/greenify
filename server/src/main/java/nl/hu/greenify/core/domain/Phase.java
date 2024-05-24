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
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.enums.PhaseName;

@Getter
@Setter
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

    // TODO: Create static named constructor
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
