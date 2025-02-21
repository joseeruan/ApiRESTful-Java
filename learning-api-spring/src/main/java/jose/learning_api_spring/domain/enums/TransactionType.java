package jose.learning_api_spring.domain.enums;

public enum TransactionType {
    INCOME("Income"),
    EXPENSE("Expense");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
