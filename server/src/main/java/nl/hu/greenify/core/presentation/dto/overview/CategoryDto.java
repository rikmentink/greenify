package nl.hu.greenify.core.presentation.dto.overview;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nl.hu.greenify.core.domain.Survey;

@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private List<SubfactorDto> subfactors;

    public CategoryDto(Long id, String name, List<SubfactorDto> subfactors) {
        this.id = id;
        this.name = name;
        this.subfactors = subfactors;
    }

    public static List<CategoryDto> fromEntities(Survey survey) {
        if (survey.getCategories().isEmpty())
            throw new IllegalArgumentException("Survey should have at least one category");

        return survey.getCategories().stream()
                        .map(category -> new CategoryDto(
                             category.getId(),
                             category.getName(),
                             SubfactorDto.fromEntities(category.getSubfactors()).stream()
                                    .sorted(Comparator.comparing(SubfactorDto::getNumber)).toList()))
                        .collect(Collectors.toList());
    }
}
