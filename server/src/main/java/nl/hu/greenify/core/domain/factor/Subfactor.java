package nl.hu.greenify.core.domain.factor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Subfactor implements IFactor {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int number;
    private boolean isSupportingFactor;

    @Setter
    @ManyToOne
    private Factor factor;

    protected Subfactor() {
    }

    public Subfactor(Long id, String title, int number, boolean isSupportingFactor, Factor factor) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.isSupportingFactor = isSupportingFactor;
        this.factor = factor;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setNumber(int number) {

    }
}
