package nl.hu.greenify.core.data;

import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findByPerson(Person person);
}
