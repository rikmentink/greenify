package nl.hu.greenify.core.presentation.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;

@Getter
@EqualsAndHashCode
public class SubmitResponseDto {
    @NonNull
    private Long subfactorId;
    private FacilitatingFactor facilitatingFactor;
    private Priority priority;
    private String comment;
    
    public SubmitResponseDto(Long subfactorId, FacilitatingFactor facilitatingFactor, Priority priority, String comment) {
        this.subfactorId = subfactorId;
        this.facilitatingFactor = facilitatingFactor;
        this.priority = priority;
        this.comment = comment;
    }

    public String toJsonString() {
        return "{" +
                "\"subfactorId\":" + subfactorId + "," +
                "\"facilitatingFactor\":\"" + facilitatingFactor + "\"," +
                "\"priority\":\"" + priority + "\"," +
                "\"comment\":\"" + comment + "\"" +
                "}";
    }
}
