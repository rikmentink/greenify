package nl.hu.greenify.core.application;

import jakarta.transaction.Transactional;
import nl.hu.greenify.core.data.*;
import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class SurveyServiceIntegrationTest {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private InterventionService interventionService;
    @Autowired
    private InterventionRepository interventionRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private PhaseRepository phaseRepository;
    @Autowired
    private PersonRepository personRepository;
    private Long phaseId1;
    private Long phaseId2;
    private Long surveyId1;
    private Long surveyId2;
    private Person person;

    @BeforeEach
    public void setup() {
        this.interventionService = new InterventionService(this.interventionRepository, phaseRepository, personService);
        this.personService = new PersonService(personRepository);
        this.surveyService = new SurveyService(surveyRepository, templateRepository, interventionService, personService);

        person = new Person("firstName", "lastName", "test@gmail.com");
        personRepository.save(person);

        Phase phase1 = new Phase(PhaseName.PLANNING);
        phase1 = phaseRepository.save(phase1);
        this.phaseId1 = phase1.getId();

        Phase phase2 = new Phase(PhaseName.EXECUTION);
        phase2 = phaseRepository.save(phase2);
        this.phaseId2 = phase2.getId();


        Template template = Template.createTemplate("Template", "Description", 1, new ArrayList<>());
        templateRepository.save(template);

        this.surveyId1 = surveyRepository.save(Survey.createSurvey(phase1, template)).getId();
        this.surveyId2 = surveyRepository.save(Survey.createSurvey(phase2, template)).getId();
    }

    @Test
    @DisplayName("A Survey should be added to a person")
    public void addSurveyToPerson() {
        surveyService.addSurveyToPerson(person.getId(), this.phaseId1);
        assertEquals(this.surveyId1, personService.getPersonById(1L).getSurveyId());
    }

    @Test
    @DisplayName("The same survey should not be added to a person twice")
    public void addSurveyToPersonTwice() {
        surveyService.addSurveyToPerson(person.getId(), this.phaseId1);

        assertThrows(
            IllegalArgumentException.class,
            () -> surveyService.addSurveyToPerson(person.getId(), this.phaseId1));
    }

    @Test
    @DisplayName("A survey should only be added twice if the phases are different")
    public void addSurveyToPersonTwiceDifferentPhase() {
        surveyService.addSurveyToPerson(person.getId(), this.phaseId1);
        surveyService.addSurveyToPerson(person.getId(), this.phaseId2);
        assertEquals(this.surveyId2, personService.getPersonById(person.getId()).getSurveyId());
    }
}
