package nl.hu.greenify.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.hu.greenify.domain.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findFirstByOrderByVersionDesc();
}
