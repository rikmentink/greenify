package nl.hu.greenify.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;

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

    @Setter
    @Enumerated(EnumType.STRING)
    private FacilitatingFactor facilitatingFactor;

    @Setter
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Response() {
        this.score = 0;
        this.facilitatingFactor = FacilitatingFactor.PENDING;
        this.priority = Priority.PENDING;
    }

    public void calculateScore() {
        this.score = facilitatingFactor.getValue() * priority.getValue();
    }
}
