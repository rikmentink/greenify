package nl.hu.greenify.core.presentation.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class CreateInterventionDto {
    private Long adminId;
    private String name;
    private String description;

    public CreateInterventionDto(Long adminId, String name, String description) {
        this.adminId = adminId;
        this.name = name;
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