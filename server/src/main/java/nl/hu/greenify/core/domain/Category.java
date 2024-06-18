package nl.hu.greenify.core.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Setter
    @OneToMany(cascade=CascadeType.ALL)
    private List<Factor> factors;

    protected Category() {
    }

    private Category(String name, String color, String description, List<Factor> factors) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.factors = factors;
    }
    
    public Category(Long id, String name, String color, String description, List<Factor> factors) {
        this(name, color, description, factors);
        this.id = id;
    }

    public static Category createCategory(String name, String color, String description) {
        return new Category(name, color, description, new ArrayList<>());
    }

    public static Category copyOf(Category original) {
        Category copy = new Category();
        copy.name = original.name;
        copy.color = original.color;
        copy.description = original.description;
        copy.factors = new ArrayList<>();

        // Make a deep copy of the factors using its static named constructor
        for (Factor factor : original.factors) {
            copy.factors.add(Factor.copyOf(factor));
        }
        return copy;
    }

    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }

    public double getProgress() {
        List<Factor> factors = this.getFactors();

        int totalSubfactors = factors.stream()
                .mapToInt(factor -> factor.getSubfactors().size())
                .sum();

        int completedSubfactors = factors.stream()
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getResponse() != null)
                .collect(Collectors.toList())
                .size();

        return (double) completedSubfactors / totalSubfactors;
    }

    public List<Subfactor> getSubfactors() {
        return this.factors.stream()
                .flatMap(factor -> factor.getSubfactors().stream())
                .collect(Collectors.toList());
    }
}
