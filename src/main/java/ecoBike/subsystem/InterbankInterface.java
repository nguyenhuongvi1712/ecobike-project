package ecoBike.subsystem;

import ecoBike.common.exception.PaymentException;
import ecoBike.common.exception.UnrecognizedException;
import ecoBike.entity.payment.CreditCard;
import ecoBike.entity.payment.PaymentTransaction;

public interface InterbankInterface {
    public abstract PaymentTransaction payOrder(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;

    public abstract PaymentTransaction refund(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;

}
