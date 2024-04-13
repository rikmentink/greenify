package nl.hu.greenify.domain.factor;

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
    private String title;
    private int number;
    private boolean isSupportingFactor;

    @ManyToOne
    private IFactor parentIFactor;

    public Subfactor(Long id, String title, int number, boolean isSupportingFactor, IFactor parentIFactor) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.isSupportingFactor = isSupportingFactor;
        this.parentIFactor = parentIFactor;
    }
}
