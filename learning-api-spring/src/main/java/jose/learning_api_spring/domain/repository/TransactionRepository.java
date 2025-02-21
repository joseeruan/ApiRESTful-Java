package jose.learning_api_spring.domain.repository;

import jose.learning_api_spring.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
