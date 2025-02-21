package jose.learning_api_spring.service.impl;

import jose.learning_api_spring.domain.model.Wallet;
import jose.learning_api_spring.domain.model.Transaction;
import jose.learning_api_spring.domain.model.Budget;
import jose.learning_api_spring.domain.repository.WalletRepository;
import jose.learning_api_spring.service.WalletService;
import jose.learning_api_spring.service.TransactionService;
import jose.learning_api_spring.service.BudgetService;
import jose.learning_api_spring.service.exception.BusinessException;
import jose.learning_api_spring.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;


    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(readOnly = true)
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Wallet findById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Wallet create(Wallet walletToCreate) {
        if (walletToCreate.getName() == null || walletToCreate.getName().isBlank()) {
            throw new BusinessException("Wallet name cannot be null or empty.");
        }
        if (walletToCreate.getBalance() == null) {
            walletToCreate.setBalance(BigDecimal.ZERO);
        }
        return walletRepository.save(walletToCreate);
    }

    @Transactional
    public Wallet update(Long id, Wallet walletToUpdate) {
        Wallet dbWallet = findById(id);
        if (walletToUpdate.getName() != null && walletToUpdate.getName().isBlank()) {
            throw new BusinessException("Wallet name cannot be empty.");
        }
        if (walletToUpdate.getName() != null) {
            dbWallet.setName(walletToUpdate.getName());
        }
        if (walletToUpdate.getBalance() != null) {
            dbWallet.setBalance(walletToUpdate.getBalance());
        }
        return walletRepository.save(dbWallet);
    }

    @Transactional
    public void delete(Long id) {
        Wallet wallet = findById(id);
        walletRepository.delete(wallet);
    }

    @Transactional(readOnly = true)
    public Set<Transaction> getTransactions(Long walletId) {
        Wallet wallet = findById(walletId);
        return wallet.getTransactions();
    }

    @Transactional(readOnly = true)
    public Set<Budget> getBudgets(Long walletId) {
        Wallet wallet = findById(walletId);
        return wallet.getBudgets();
    }
}