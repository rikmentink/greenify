package nl.hu.greenify.core.presentation.dto.overview;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nl.hu.greenify.core.domain.factor.Subfactor;

@Getter
public class SubfactorDto {
    private Long id;
    private int number;

    public SubfactorDto(Long id, int number) {
        this.id = id;
        this.number = number;
    }

    public static List<SubfactorDto> fromEntities(List<Subfactor> subfactors) {
        return subfactors.stream()
                .map(subfactor -> new SubfactorDto(
                     subfactor.getId(),
                     subfactor.getNumber()))
                .collect(Collectors.toList());
    }
}
