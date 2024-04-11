package domain;

import domain.interfaces.IFactor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
    private IFactor factors;
    
    public Category(Long id, String name, String color, String description, IFactor factors) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.factors = factors;
    }
}
