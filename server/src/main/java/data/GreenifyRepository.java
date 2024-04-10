package data;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenifyRepository extends JpaRepository<User, Long>{
}
