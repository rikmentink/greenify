package nl.hu.greenify.core.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.hu.greenify.core.domain.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findFirstByOrderByVersionDesc();
}
