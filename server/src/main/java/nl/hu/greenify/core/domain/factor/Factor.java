package nl.hu.greenify.core.domain.factor;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.interfaces.IFactor;

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
    @ManyToOne
    private Category category;

    @OneToMany
    private List<Subfactor> subfactors = new ArrayList<>();

    protected Factor() {
    }

    public Factor(Long id, String title, int number, Category category) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.category = category;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setNumber(int number) {

    }

    public void addSubfactor(Subfactor subfactor) {
        this.subfactors.add(subfactor);
        subfactor.setFactor(this);
    }
}
