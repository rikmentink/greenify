package nl.hu.greenify.core.domain;

import java.util.List;

import javax.print.DocFlavor.READER;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Survey extends Template {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Phase phase;

    protected Survey() {
    }

    public Survey(String name, String description, Integer version, List<Category> categories, Phase phase) {
        super(name, description, version, categories);
        this.phase = phase;
    }

    public static Survey createSurvey(Phase phase, Template activeTemplate) {
        if (phase == null || phase.getName() == null)
            throw new IllegalArgumentException("A phase cannot be empty or null.");
        if (activeTemplate == null || activeTemplate.getCategories() == null)
            throw new IllegalArgumentException("A template cannot be empty or null.");
        
        return new Survey(
            activeTemplate.getName(),
            activeTemplate.getDescription(),
            activeTemplate.getVersion(),
            activeTemplate.getCategories(),
            phase
        );
    }

    public Phase getPhase() {
        return this.phase;
    }
}
