package nl.hu.greenify.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.domain.Intervention;

@Getter
public class InterventionDto {
    private Long id;
    private String name;
    private String description;

    public InterventionDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static InterventionDto fromEntity(Intervention intervention) {
        return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription());
    }
}
