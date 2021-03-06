package ecoBike.subsystem;

import ecoBike.entity.payment.CreditCard;
import ecoBike.entity.payment.PaymentTransaction;
import ecoBike.subsystem.interbank.InterbankSubsystemController;

public class InterbankSubsystem implements InterbankInterface{
    private InterbankSubsystemController ctrl;

    public InterbankSubsystem() {
        String str = new String();
        this.ctrl = new InterbankSubsystemController();
    }

    public PaymentTransaction payOrder(CreditCard card, int amount, String contents) {
        PaymentTransaction transaction = ctrl.payOrder(card, amount, contents);
        return transaction;
    }

    public PaymentTransaction refund(CreditCard card, int amount, String contents) {
        PaymentTransaction transaction = ctrl.refund(card, amount, contents);
        return transaction;
    }
}
