package nl.hu.greenify.core.presentation.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import nl.hu.greenify.core.domain.enums.PhaseName;

@Getter
public class CreatePhaseDto {
    private PhaseName phaseName;
    private String description;

    public CreatePhaseDto(PhaseName phaseName, String description) {
        this.phaseName = phaseName;
        this.description = description;
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