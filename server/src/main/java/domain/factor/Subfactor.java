package domain.factor;

import domain.interfaces.IFactor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subfactor implements IFactor {
    private Long id;
    private IFactor parentIFactor;
    private boolean isParentFactor;

    public Subfactor(IFactor parentIFactor, boolean isParentFactor) {
        this.parentIFactor = parentIFactor;
        this.isParentFactor = isParentFactor;
    }

    @Override
    public String toString() {
        return "Subfactor{" +
                "id=" + id +
                ", parentFactor=" + parentIFactor +
                ", isParentFactor=" + isParentFactor +
                '}';
    }
}
