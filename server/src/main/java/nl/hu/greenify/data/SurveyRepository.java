package nl.hu.greenify.data;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.hu.greenify.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
