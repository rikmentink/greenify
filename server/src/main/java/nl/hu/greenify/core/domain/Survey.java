package nl.hu.greenify.core.domain;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.ToString;
import nl.hu.greenify.core.application.exceptions.SubfactorNotFoundException;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Subfactor;

@Entity
@Getter
@ToString
public class Survey {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
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
        if (phase != null) {
            phase.addSurvey(this);
        }
    }

    public Survey(Long id, Phase phase, List<Category> categories, Person respondent) {
        this.id = id;
        this.phase = phase;
        this.categories = categories;
        this.respondent = respondent;
        if (phase != null) {
            phase.addSurvey(this);
        }
    }

    public static Survey createSurvey(Phase phase, Template activeTemplate, Person respondent) {
        if (phase == null || phase.getName() == null)
            throw new IllegalArgumentException("A phase cannot be empty or null.");
        if (activeTemplate == null || activeTemplate.getCategories() == null)
            throw new IllegalArgumentException("A template cannot be empty or null.");
        if (respondent == null || respondent.hasSurvey(phase))
            throw new IllegalArgumentException("A respondent cannot be empty or null or have a survey for this phase.");
        
        return new Survey(
            phase,
            cloneCategories(activeTemplate.getCategories()),
            respondent
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

    public Response saveResponse(Long subfactorId, FacilitatingFactor facilitatingFactor, Priority priority, String comment) {
        Subfactor subfactor = this.getSubfactorById(subfactorId);
        Response response = Response.createResponse(
            this.getSubfactorById(subfactorId), 
            facilitatingFactor, priority, comment
        );

        subfactor.setResponse(response);
        return response;
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
