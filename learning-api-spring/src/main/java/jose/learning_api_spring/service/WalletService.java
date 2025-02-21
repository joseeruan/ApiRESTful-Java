package jose.learning_api_spring.service;

import jose.learning_api_spring.domain.model.Wallet;
import jose.learning_api_spring.domain.model.Budget;
import jose.learning_api_spring.domain.model.Transaction;

import java.util.Set;

public interface WalletService extends CrudService<Long, Wallet> {

    Set<Transaction> getTransactions(Long walletId);
    Set<Budget> getBudgets(Long walletId);
}