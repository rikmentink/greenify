package domain.factor;

import domain.interfaces.IFactor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class Factor implements IFactor {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<IFactor> subfactors;

    public Factor(Long id, List<IFactor> subfactors) {
        this.id = id;
        this.subfactors = subfactors;
    }
}
