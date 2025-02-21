package jose.learning_api_spring.controller.dto;

import jose.learning_api_spring.domain.model.Wallet;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record WalletDto(
        Long id,
        String name,
        BigDecimal balance,
        Set<BudgetDto> budgets,
        Set<TransactionDto> transactions
) {
    public WalletDto(Wallet model) {
        this(
                model.getId(),
                model.getName(),
                model.getBalance(),
                model.getBudgets() != null
                        ? model.getBudgets().stream().map(BudgetDto::new).collect(Collectors.toSet())
                        : Collections.emptySet(),
                model.getTransactions() != null
                        ? model.getTransactions().stream().map(TransactionDto::new).collect(Collectors.toSet())
                        : Collections.emptySet()
        );
    }

    public Wallet toModel() {
        Wallet wallet = new Wallet();
        wallet.setId(this.id);
        wallet.setName(this.name);
        wallet.setBalance(this.balance);
        if (this.budgets != null) {
            wallet.setBudgets(this.budgets.stream().map(BudgetDto::toModel).collect(Collectors.toSet()));
        }
        if (this.transactions != null) {
            wallet.setTransactions(this.transactions.stream().map(TransactionDto::toModel).collect(Collectors.toSet()));
        }
        return wallet;
    }
}