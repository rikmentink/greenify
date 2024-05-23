package nl.hu.greenify.core.data;

import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.factor.Factor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactorRepository extends JpaRepository<Factor, Long> {
}
