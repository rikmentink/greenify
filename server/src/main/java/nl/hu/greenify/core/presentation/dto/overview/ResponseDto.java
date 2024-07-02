package nl.hu.greenify.core.presentation.dto.overview;

import lombok.Getter;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Subfactor;

@Getter
public class ResponseDto {
    private Long subfactorId;
    private int subfactorNumber;
    private boolean facilitatingFactor;
    private boolean priority;

    public ResponseDto(Long subfactorId, int subfactorNumber, boolean facilitatingFactor, boolean priority) {
        this.subfactorId = subfactorId;
        this.subfactorNumber = subfactorNumber;
        this.facilitatingFactor = facilitatingFactor;
        this.priority = priority;
    }

    public static ResponseDto fromEntity(Subfactor subfactor) {
        boolean hasFacilitatingFactor = subfactor.getResponse().getFacilitatingFactor() != null
                && !subfactor.getResponse().getFacilitatingFactor().equals(FacilitatingFactor.PENDING);
        boolean hasPriority = subfactor.getResponse().getPriority() != null
                && !subfactor.getResponse().getPriority().equals(Priority.PENDING);

        return new ResponseDto(
                subfactor.getId(),
                subfactor.getNumber(),
                hasFacilitatingFactor,
                hasPriority);
    }
}
