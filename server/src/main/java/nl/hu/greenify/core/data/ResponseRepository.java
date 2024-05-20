package nl.hu.greenify.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.hu.greenify.core.domain.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
