package nl.hu.greenify.core.domain;

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
    private Long id;

    private double score;

    @Setter
    private String comment;

    @Enumerated(EnumType.STRING)
    private FacilitatingFactor facilitatingFactor;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToOne
    private Subfactor subfactor;

    protected Response() {
    }

    private Response(double score, String comment, FacilitatingFactor facilitatingFactor, Priority priority,
            Subfactor subfactor) {
        this.score = score;
        this.comment = comment;
        this.facilitatingFactor = facilitatingFactor;
        this.priority = priority;
        this.subfactor = subfactor;
    }

    public Response(Long id, double score, String comment, FacilitatingFactor facilitatingFactor, Priority priority,
            Subfactor subfactor) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.facilitatingFactor = facilitatingFactor;
        this.priority = priority;
        this.subfactor = subfactor;
    }

    public static Response createResponse(Subfactor subfactor, FacilitatingFactor facilitatingFactor, Priority priority, String comment) {
        Response response = new Response(
            0,
            null,
            facilitatingFactor,
            priority,
            subfactor
        );
        response.subfactor.setResponse(response);
        return response;
    }

    private void calculateScore() {
        boolean isSupportingFactor = this.subfactor.isSupportingFactor();
        this.score = facilitatingFactor.getValue(isSupportingFactor) * priority.getValue();
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
