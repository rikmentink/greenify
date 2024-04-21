package nl.hu.greenify.core.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.hu.greenify.core.domain.factor.Factor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String color;
    private String description;

    @OneToMany
    private List<Factor> factors = new ArrayList<>();

    protected Category() {
    }
    
    public Category(Long id, String name, String color, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public static Category copyOf(Category original) {
        Category copy = new Category();
        copy.id = original.id;
        copy.name = original.name;
        copy.color = original.color;
        copy.description = original.description;
        // Make a deep copy of the factors using its static named constructor
        for (Factor factor : original.factors) {
            copy.factors.add(Factor.copyOf(factor));
        }
        return copy;
    }

    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }
}
