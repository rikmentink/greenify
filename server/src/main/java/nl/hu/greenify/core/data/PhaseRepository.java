package nl.hu.greenify.core.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.hu.greenify.core.domain.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    // public Optional<Phase> findById(Long id);
}
