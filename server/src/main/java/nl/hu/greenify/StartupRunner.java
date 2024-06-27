package nl.hu.greenify;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nl.hu.greenify.core.application.SurveyService;

@Component
@ConditionalOnProperty(name="app.startup.runner.enabled", havingValue="true", matchIfMissing=true)
public class StartupRunner implements CommandLineRunner {
    private final SurveyService surveyService;

    public StartupRunner(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.surveyService.createDefaultTemplate();
    }
}
