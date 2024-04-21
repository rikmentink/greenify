package nl.hu.greenify.core.domain.factor;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Factor implements IFactor {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int number;

    @OneToMany
    private List<Subfactor> subfactors = new ArrayList<>();

    protected Factor() {
    }

    public Factor(Long id, String title, int number) {
        this.id = id;
        this.title = title;
        this.number = number;
    }

    public static Factor copyOf(Factor original) {
        Factor copy = new Factor();
        copy.id = original.id;
        copy.title = original.title;
        copy.number = original.number;
        // Make a deep copy of the subfactors using its static named constructor
        for (Subfactor subfactor : original.subfactors) {
            copy.subfactors.add(Subfactor.copyOf(subfactor));
        }
        return copy;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setNumber(int number) {

    }

    public void addSubfactor(Subfactor subfactor) {
        this.subfactors.add(subfactor);
    }
}
