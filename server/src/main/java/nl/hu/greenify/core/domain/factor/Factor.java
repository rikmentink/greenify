package nl.hu.greenify.core.domain.factor;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Setter
    @OneToMany
    private List<Subfactor> subfactors = new ArrayList<>();

    protected Factor() {
    }

    private Factor(String title, int number) {
        this.title = title;
        this.number = number;
    }

    public Factor(Long id, String title, int number, List<Subfactor> subfactors) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.subfactors = subfactors;
    }

    public static Factor createFactor(String title, int number) {
        return new Factor(title, number);
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
