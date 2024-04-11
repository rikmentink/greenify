package nl.hu.greenify.domain.factor;

import nl.hu.greenify.domain.interfaces.IFactor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Factor implements IFactor {
    private Long id;
    private List<IFactor> subfactors;

    public Factor(List<IFactor> subfactors) {
        this.subfactors = subfactors;
    }

    @Override
    public String toString() {
        return "Factor{" +
                "id=" + id +
                ", subfactors=" + subfactors +
                '}';
    }
}
