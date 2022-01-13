package ecoBike.common.exception;

public class NotEnoughTransactionInfoException extends PaymentException {
    public NotEnoughTransactionInfoException() {
        super("ERROR: Not Enough Transaction Information");
    }
}