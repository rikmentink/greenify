package nl.hu.greenify.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Subfactor;

@Entity
@ToString
@Getter
public class Response {
    @Id
    @GeneratedValue
    @Setter
    private Long id;

    private double score;

    private boolean isForSupportingFactor;

    @Setter
    private String comment;

    @Enumerated(EnumType.STRING)
    @JsonFormat(shape=JsonFormat.Shape.NUMBER)
    private FacilitatingFactor facilitatingFactor;

    @Enumerated(EnumType.STRING)
    @JsonFormat(shape=JsonFormat.Shape.NUMBER)
    private Priority priority;

    protected Response() {
    }

    private Response(double score, String comment, FacilitatingFactor facilitatingFactor, Priority priority,
            Subfactor subfactor) {
        this.score = score;
        this.comment = comment;
        this.facilitatingFactor = facilitatingFactor;
        this.priority = priority;
        this.isForSupportingFactor = subfactor.isSupportingFactor();
    }

    public Response(Long id, double score, String comment, FacilitatingFactor facilitatingFactor, Priority priority,
            Subfactor subfactor) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.facilitatingFactor = facilitatingFactor;
        this.priority = priority;
        this.isForSupportingFactor = subfactor.isSupportingFactor();
    }

    public static Response createResponse(Subfactor subfactor, FacilitatingFactor facilitatingFactor, Priority priority, String comment) {
        Response response = new Response(
            0,
            comment,
            facilitatingFactor,
            priority,
            subfactor
        );
        
        if (subfactor.getResponse() != null) {
            response.setId(subfactor.getResponse().getId());
        }
        subfactor.setResponse(response);
        response.calculateScore();
        return response;
    }

    private void calculateScore() {
        this.score = facilitatingFactor.getValue(this.isForSupportingFactor) * priority.getValue();
    }

    public void setFacilitatingFactor(FacilitatingFactor facilitatingFactor) {
        this.facilitatingFactor = facilitatingFactor;
        this.calculateScore();
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        this.calculateScore();
    }
}
