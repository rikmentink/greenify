package nl.hu.greenify.core.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL)
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

    public List<Subfactor> getAllSubfactors() {
        return this.getCategories().stream()
                .flatMap(category -> category.getFactors().stream())
                .flatMap(factor -> factor.getSubfactors().stream())
                .collect(Collectors.toList());
    }

    public Subfactor getSubfactorById(Long id) {
        return this.getCategories().stream()
                .flatMap(category -> category.getFactors().stream())
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new SubfactorNotFoundException("Subfactor with ID " + id + " not found."));
    }

    public Response saveResponse(Long subfactorId, FacilitatingFactor facilitatingFactor, Priority priority, String comment) {
        return Response.createResponse(
            this.getSubfactorById(subfactorId), 
            facilitatingFactor, priority, comment
        );
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public double getProgress() {
        return this.categories.stream()
                .mapToDouble(Category::getProgress)
                .average()
                .orElse(0);
    }

    public double getProgressByCategory(Category category) {
        if (this.categories.contains(category) == false)
            throw new IllegalArgumentException("Category not found in survey.");

        return category.getProgress();
    }

    private static List<Category> cloneCategories(List<Category> templateCategories) {
        return templateCategories.stream()
                .map(Category::copyOf)
                .collect(Collectors.toList());
    }
}
