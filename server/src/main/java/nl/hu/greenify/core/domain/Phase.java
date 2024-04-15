package nl.hu.greenify.core.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    private Intervention intervention;

    @OneToMany
    private List<Survey> surveys;

    public Phase(PhaseName name, Intervention intervention, List<Survey> surveys) {
        this.name = name;
        this.intervention = intervention;
        this.surveys = surveys;
    }

    public static Phase createPhase(PhaseName name, Intervention intervention) {
        if(name == null) {
            throw new IllegalArgumentException("Phase should have a name");
        }
        return new Phase(name, intervention, new ArrayList<>());
    }

    protected Phase() {
    }
}
