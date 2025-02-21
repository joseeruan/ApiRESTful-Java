package jose.learning_api_spring.domain.repository;

import jose.learning_api_spring.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    boolean existsByEmail(String number);
}
