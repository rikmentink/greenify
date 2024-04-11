package nl.hu.greenify.data;

import nl.hu.greenify.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenifyRepository extends JpaRepository<User, Long>{
}
