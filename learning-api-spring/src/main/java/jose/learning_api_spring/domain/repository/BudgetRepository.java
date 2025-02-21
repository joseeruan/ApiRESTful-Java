package jose.learning_api_spring.domain.repository;

import jose.learning_api_spring.domain.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {



}
