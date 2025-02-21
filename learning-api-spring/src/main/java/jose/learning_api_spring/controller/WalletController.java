package jose.learning_api_spring.controller;

import jose.learning_api_spring.controller.dto.WalletDto;
import jose.learning_api_spring.controller.dto.BudgetDto;
import jose.learning_api_spring.controller.dto.TransactionDto;
import jose.learning_api_spring.domain.model.Budget;
import jose.learning_api_spring.domain.model.Transaction;
import jose.learning_api_spring.domain.model.Wallet;
import jose.learning_api_spring.domain.model.User;
import jose.learning_api_spring.domain.repository.UserRepository;
import jose.learning_api_spring.service.WalletService;
import jose.learning_api_spring.service.TransactionService;
import jose.learning_api_spring.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final UserRepository userRepository;

    @Autowired
    public WalletController(WalletService walletService, TransactionService transactionService,
                            BudgetService budgetService, UserRepository userRepository) {
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<WalletDto> findAll() {
        return walletService.findAll().stream().map(WalletDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WalletDto findById(@PathVariable Long id) {
        return new WalletDto(walletService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletDto create(@RequestBody WalletDto walletDto, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        Wallet wallet = walletDto.toModel();
        wallet.setUser(user);
        wallet = walletService.create(wallet);
        return new WalletDto(wallet);
    }

    @PutMapping("/{id}")
    public WalletDto update(@PathVariable Long id, @RequestBody WalletDto walletDto) {
        Wallet wallet = walletDto.toModel();
        wallet.setId(id);
        return new WalletDto(walletService.update(id, wallet));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        walletService.delete(id);
    }

    @GetMapping("/{walletId}/transactions")
    public Set<TransactionDto> getTransactions(@PathVariable Long walletId) {
        return walletService.getTransactions(walletId).stream().map(TransactionDto::new).collect(Collectors.toSet());
    }

    @PostMapping("/{walletId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@PathVariable Long walletId, @RequestBody TransactionDto transactionDto) {
        Wallet wallet = walletService.findById(walletId);
        Transaction transaction = transactionDto.toModel();
        transaction.setWallet(wallet);
        return new TransactionDto(transactionService.create(transaction));
    }

    @GetMapping("/{walletId}/budgets")
    public Set<BudgetDto> getBudgets(@PathVariable Long walletId) {
        return walletService.getBudgets(walletId).stream().map(BudgetDto::new).collect(Collectors.toSet());
    }

    @PostMapping("/{walletId}/budgets")
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetDto createBudget(@PathVariable Long walletId, @RequestBody BudgetDto budgetDto) {
        Wallet wallet = walletService.findById(walletId);
        Budget budget = budgetDto.toModel();
        budget.setWallet(wallet);
        return new BudgetDto(budgetService.create(budget));
    }
}