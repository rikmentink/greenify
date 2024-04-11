package data;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
