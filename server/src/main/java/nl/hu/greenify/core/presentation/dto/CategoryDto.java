package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.factor.Factor;

import java.util.List;

@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private String color;
    private String description;
    private List<Factor> factors;

    public CategoryDto(Long id, String name, String color, String description, List<Factor> factors) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.factors = factors;
    }
}
