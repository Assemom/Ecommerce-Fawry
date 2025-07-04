package exceptions;

public class NoAvailableStockException extends RuntimeException {
    public NoAvailableStockException(String message) {
        super(message);
    }
}