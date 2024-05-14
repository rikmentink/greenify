package nl.hu.greenify.core.domain;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;
import nl.hu.greenify.core.application.exceptions.SubfactorNotFoundException;
import nl.hu.greenify.core.domain.factor.Subfactor;

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

    private Survey(String name, String description, Integer version, List<Category> categories, Phase phase) {
        super(name, description, version, categories);
        this.phase = phase;
        if (phase != null) {
            phase.addSurvey(this);
        }
    }

    public Survey(Long id, String name, String description, Integer version, List<Category> categories, Phase phase) {
        super(id, name, description, version, categories);
        this.phase = phase;
        if (phase != null) {
            phase.addSurvey(this);
        }
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
            cloneCategories(activeTemplate.getCategories()),
            phase
        );
    }

    public Long getPhaseId() {
        return phase.getId();
    }

    public Subfactor getSubfactorById(Long id) {
        return this.getCategories().stream()
                .flatMap(category -> category.getFactors().stream())
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new SubfactorNotFoundException("Subfactor with ID " + id + " not found."));
    }

    private static List<Category> cloneCategories(List<Category> templateCategories) {
        return templateCategories.stream()
                .map(Category::copyOf)
                .collect(Collectors.toList());
    }
}
