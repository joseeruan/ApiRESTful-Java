package jose.learning_api_spring.controller.dto;

import jose.learning_api_spring.domain.model.Transaction;
import jose.learning_api_spring.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        Long id,
        BigDecimal amount,
        TransactionType type,
        LocalDateTime date

) {

    public TransactionDto(Transaction model) {
        this(
                model.getId(),
                model.getAmount(),
                model.getType(),
                model.getDate()
        );
    }

    public Transaction toModel() {
        Transaction model = new Transaction();
        model.setId(this.id);
        model.setAmount(this.amount);
        model.setType(this.type);
        model.setDate(this.date);
        return model;
    }
}