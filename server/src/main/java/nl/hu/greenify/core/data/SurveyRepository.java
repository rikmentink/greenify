package nl.hu.greenify.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.hu.greenify.core.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
