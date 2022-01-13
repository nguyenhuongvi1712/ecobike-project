package ecoBike.common.exception;

public class InvalidBarcodeException extends RuntimeException{
    public InvalidBarcodeException() {
        super("ERROR: Invalid barcode!");
    }
    public InvalidBarcodeException(String messange) {
        super(messange);
    }
}
