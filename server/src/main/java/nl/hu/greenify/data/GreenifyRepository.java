package nl.hu.greenify.data;

import nl.hu.greenify.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenifyRepository extends JpaRepository<Person, Long> {
}
