package nl.hu.greenify.core.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.ToString;
import nl.hu.greenify.core.application.exceptions.SubfactorNotFoundException;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

@Entity
@Getter
@ToString
public class Survey {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phase_id")
    @JsonIgnore
    private Phase phase;

    @OneToMany
    private List<Category> categories;

    @ManyToOne
    private Person respondent;

    protected Survey() {
    }

    private Survey(Phase phase, List<Category> categories, Person respondent) {
        this.phase = phase;
        this.categories = categories;
        this.respondent = respondent;
    }

    public Survey(Long id, Phase phase, List<Category> categories, Person respondent) {
        this.id = id;
        this.phase = phase;
        this.categories = categories;
        this.respondent = respondent;
    }

    public static Survey createSurvey(Phase phase, Template activeTemplate, Person respondent) {
        if (phase == null || phase.getName() == null)
            throw new IllegalArgumentException("A phase cannot be empty or null.");
        if (activeTemplate == null || activeTemplate.getCategories() == null)
            throw new IllegalArgumentException("A template cannot be empty or null.");
        if (respondent == null || respondent.hasSurvey(phase))
            throw new IllegalArgumentException("A respondent cannot be empty or null or have a survey for this phase.");
        
        Survey survey = new Survey(
            phase,
            cloneCategories(activeTemplate.getCategories()),
            respondent
        );
        respondent.addSurvey(survey);
        phase.addSurvey(survey);
        return survey;
    }

    public Long getPhaseId() {
        return phase.getId();
    }

    public Category getCategoryById(Long id) {
        return this.getCategories().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + id + " not found."));
    }

    public List<Factor> getAllFactors() {
        return this.getCategories().stream()
                .flatMap(category -> category.getFactors().stream())
                .collect(Collectors.toList());
    }

    public List<Factor> getFactorsByCategoryId(Long categoryId) {
        return this.getCategoryById(categoryId).getFactors();
    }

    public Subfactor getSubfactorById(Long id) {
        return this.getCategories().stream()
                .flatMap(category -> category.getFactors().stream())
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new SubfactorNotFoundException("Subfactor with ID " + id + " not found."));
    }

    /**
     * TODO: Update existing response if it exists.
     */
    public Response saveResponse(Long subfactorId, FacilitatingFactor facilitatingFactor, Priority priority, String comment) {
        Subfactor subfactor = this.getSubfactorById(subfactorId);
        return Response.createResponse(
            this.getSubfactorById(subfactorId), 
            facilitatingFactor, priority, comment
        );
    }

    public Person getRespondent() {
        return this.respondent;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    private static List<Category> cloneCategories(List<Category> templateCategories) {
        return templateCategories.stream()
                .map(Category::copyOf)
                .collect(Collectors.toList());
    }
}
