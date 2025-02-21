package jose.learning_api_spring.domain.repository;

import jose.learning_api_spring.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByName(String name);
}
