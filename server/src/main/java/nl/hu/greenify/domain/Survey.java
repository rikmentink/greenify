package nl.hu.greenify.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Survey extends Template {
    @Id
    @GeneratedValue
    private Long id;

    protected Survey() {
    }

    public Survey(Long id, String name, String description, Integer version, List<Category> questions) {
        super(id, name, description, version, questions);
    }

    public static Survey createSurvey(Phase phase, Template activeTemplate) {
        return null;
    }

    public Phase getPhase() {
        return null;
    }

    public Template getTemplate() {
        return null;
    }
}
