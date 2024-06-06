package nl.hu.greenify.core.presentation.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import nl.hu.greenify.core.domain.enums.PhaseName;
import java.util.List;

@Getter
public class PhaseDto {
    private Long id;
    private String name;
    private String description;
    private PhaseName phase;
    private List<CategoryDto> categories;

    public PhaseDto(Long id, String name, String description, PhaseName phase, List<CategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phase = phase;
        this.categories = categories;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert DTO to JSON.");
        }
    }
}
