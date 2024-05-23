package nl.hu.greenify.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.hu.greenify.core.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
