package jose.learning_api_spring.controller.dto;

import jose.learning_api_spring.domain.model.Budget;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetDto(
        Long id,
        BigDecimal limit,
        LocalDate startDate,
        LocalDate endDate
) {
    public BudgetDto(Budget model) {
        this(
                model.getId(),
                model.getLimit(),
                model.getStartDate(),
                model.getEndDate()
        );
    }

    public Budget toModel() {
        Budget model = new Budget();
        model.setId(this.id);
        model.setLimit(this.limit);
        model.setStartDate(this.startDate);
        model.setEndDate(this.endDate);
        return model;
    }
}