package jose.learning_api_spring.service.impl;

import jose.learning_api_spring.domain.model.Transaction;
import jose.learning_api_spring.domain.repository.TransactionRepository;
import jose.learning_api_spring.service.TransactionService;
import jose.learning_api_spring.service.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found"));
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Transaction amount must be greater than zero.");
        }
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction update(Long id, Transaction transaction) {
        Transaction dbTransaction = transactionRepository.findById(id).orElseThrow(() -> new BusinessException("Transaction not found"));

        dbTransaction.setAmount(transaction.getAmount());
        dbTransaction.setType(transaction.getType());
        dbTransaction.setDate(transaction.getDate());

        return transactionRepository.save(dbTransaction);
    }

    @Transactional
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new BusinessException("Transaction not found"));
        transactionRepository.delete(transaction);
    }
}
