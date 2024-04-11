package domain.factor;

import domain.interfaces.IFactor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Subfactor implements IFactor {
    @Id
    @GeneratedValue
    private Long id;
    private boolean isParentFactor;

    @ManyToOne
    private IFactor parentIFactor;

    public Subfactor(Long id, boolean isParentFactor, IFactor parentIFactor) {
        this.id = id;
        this.isParentFactor = isParentFactor;
        this.parentIFactor = parentIFactor;
    }
}
