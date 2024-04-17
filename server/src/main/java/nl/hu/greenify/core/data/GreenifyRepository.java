package nl.hu.greenify.core.data;

import nl.hu.greenify.core.domain.Person;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenifyRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
