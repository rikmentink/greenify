package nl.hu.greenify.core.data;

import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
