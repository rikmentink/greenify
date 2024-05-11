package nl.hu.greenify.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
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
    private PhaseName name;

    @OneToMany
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phase phase)) return false;
        return Objects.equals(getId(), phase.getId()) && getName() == phase.getName() && Objects.equals(getSurveys(), phase.getSurveys());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurveys());
    }
}
