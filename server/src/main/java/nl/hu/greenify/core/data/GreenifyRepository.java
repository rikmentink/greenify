package nl.hu.greenify.core.data;

import nl.hu.greenify.core.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenifyRepository extends JpaRepository<Person, Long> {
}