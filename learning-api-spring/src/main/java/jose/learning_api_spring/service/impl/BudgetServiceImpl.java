package jose.learning_api_spring.service.impl;

import jose.learning_api_spring.domain.model.Budget;
import jose.learning_api_spring.domain.repository.BudgetRepository;
import jose.learning_api_spring.service.BudgetService;
import jose.learning_api_spring.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Transactional(readOnly = true)
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Budget findById(Long id) {
        return budgetRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Budget create(Budget budgetToCreate) {
        return budgetRepository.save(budgetToCreate);
    }

    @Transactional
    public Budget update(Long id, Budget budgetToUpdate) {
        Budget dbBudget = this.findById(id);
        dbBudget.setLimit(budgetToUpdate.getLimit());
        dbBudget.setStartDate(budgetToUpdate.getStartDate());
        dbBudget.setEndDate(budgetToUpdate.getEndDate());
        return budgetRepository.save(dbBudget);
    }

    @Transactional
    public void delete(Long id) {
        Budget dbBudget = this.findById(id);
        budgetRepository.delete(dbBudget);
    }
}
