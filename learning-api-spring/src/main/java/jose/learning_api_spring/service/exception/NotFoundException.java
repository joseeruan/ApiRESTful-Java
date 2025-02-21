package jose.learning_api_spring.service.exception;

public class NotFoundException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("Resource not found.");
    }
}
