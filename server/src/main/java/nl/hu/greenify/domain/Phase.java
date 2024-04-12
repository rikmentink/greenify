package nl.hu.greenify.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.domain.enums.PhaseName;

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

    public Phase(PhaseName name) {
        if(name == null) {
            throw new IllegalArgumentException("Phase should have a name");
        }
        this.name = name;
    }

    protected Phase() {
    }
}
