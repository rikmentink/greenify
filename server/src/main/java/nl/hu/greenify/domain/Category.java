package nl.hu.greenify.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.hu.greenify.domain.interfaces.IFactor;

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

    public Category() {
    }
    
    public Category(Long id, String name, String color, String description, IFactor factors) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
    }
}
