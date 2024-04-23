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

    public Response(Subfactor subfactor) {
        this.subfactor = subfactor;
        this.score = 0;
        this.facilitatingFactor = FacilitatingFactor.PENDING;
        this.priority = Priority.PENDING;
        if (subfactor != null) {
            this.subfactor.setResponse(this);
        }
    }

    protected Response() {
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

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", score=" + score +
                '}';
    }
}
