package ecoBike.common.exception;;

public class InvalidCardException extends PaymentException {
    public InvalidCardException() {
        super("ERROR: Invalid card!");
    }
    public InvalidCardException(String message) {
        super(message);
    }
}
