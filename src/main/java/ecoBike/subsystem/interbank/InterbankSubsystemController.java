package ecoBike.subsystem.interbank;

import ecoBike.common.exception.*;
import ecoBike.entity.payment.CreditCard;
import ecoBike.entity.payment.PaymentTransaction;
import ecoBike.utils.Configs;
import ecoBike.utils.MyMap;
import ecoBike.utils.Utils;

import java.util.Map;

public class InterbankSubsystemController {
//    private static final String PUBLIC_KEY = "AQzdE8O/fR8=";
//    private static final String SECRET_KEY = "BUXj/7/gHHI=";
//    private static final String PAY_COMMAND = "pay";
//    private static final String VERSION = "1.0.1";

    private static InterbankBoundary interbankBoundary = new InterbankBoundary();

    public PaymentTransaction refund(CreditCard card, int amount, String contents) {
        Map<String, Object> transaction = new MyMap();

        try {
            transaction.putAll(MyMap.toMyMap(card));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new InvalidCardException("Invalid expiration date");
        }
        transaction.put("command", Configs.REFUND_CMD);
        transaction.put("transactionContent", contents);
        transaction.put("amount", amount);
        transaction.put("createdAt", Utils.getToday());

        Map<String, Object> dataForHash = new MyMap();
        dataForHash.put("secretKey", Configs.SECRET_KEY);
        dataForHash.put("transaction", transaction);
        String hashCode = Utils.md5(generateData(dataForHash));

        Map<String, Object> requestMap = new MyMap();
        requestMap.put("version", Configs.VERSION);
        requestMap.put("transaction", transaction);
        requestMap.put("appCode", Configs.APP_CODE);
        requestMap.put("hashCode", hashCode);
        String responseText = interbankBoundary.query(Configs.PROCESS_TRANSACTION_URL, generateData(requestMap));
        MyMap response = null;
        try {
            response = MyMap.toMyMap(responseText, 0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new UnrecognizedException();
        }
        return makePaymentTransaction(response);
    }

    private String generateData(Map<String, Object> data) {
        return ((MyMap) data).toJSON();
    }

    public PaymentTransaction payOrder(CreditCard card, int amount, String contents) {
        Map<String, Object> transaction = new MyMap();

        try {
            transaction.putAll(MyMap.toMyMap(card));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new InvalidCardException("Invalid expiration date");
        }
        transaction.put("command", Configs.PAY_CMD);
        transaction.put("transactionContent", contents);
        transaction.put("amount", amount);
        transaction.put("createdAt", Utils.getToday());

        Map<String, Object> dataForHash = new MyMap();
        dataForHash.put("secretKey", Configs.SECRET_KEY);
        dataForHash.put("transaction", transaction);
        String hashCode = Utils.md5(generateData(dataForHash));

        Map<String, Object> requestMap = new MyMap();
        requestMap.put("version", Configs.VERSION);
        requestMap.put("transaction", transaction);
        requestMap.put("appCode", Configs.APP_CODE);
        requestMap.put("hashCode", hashCode);
        String responseText = interbankBoundary.query(Configs.PROCESS_TRANSACTION_URL, generateData(requestMap));
        MyMap response = null;
        try {
            response = MyMap.toMyMap(responseText, 0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new UnrecognizedException();
        }
        return makePaymentTransaction(response);
    }

    private PaymentTransaction makePaymentTransaction(MyMap response) {
        if (response == null)
            return null;
        PaymentTransaction trans;
        if(response.get("transaction") != null) {
            MyMap transaction = (MyMap) response.get("transaction");
            CreditCard card = new CreditCard((String) transaction.get("cardCode"), (String) transaction.get("owner"),
                    (String) transaction.get("cvvCode"), (String) transaction.get("dateExpired"));
            trans = new PaymentTransaction((String) response.get("errorCode"), card,
                    (String) transaction.get("transactionId"), (String) transaction.get("transactionContent"),
                    Integer.parseInt((String) transaction.get("amount")), (String) transaction.get("createdAt"));
            switch (trans.getErrorCode()) {
                case "00":
                    break;
                case "01":
                    throw new InvalidCardException("Invalid expiration date");
                case "02":
                    throw new NotEnoughBalanceException();
                case "03":
                    throw new InternalServerErrorException();
                case "04":
                    throw new SuspiciousTransactionException();
                case "05":
                    throw new NotEnoughTransactionInfoException();
                case "06":
                    throw new InvalidVersionException();
                case "07":
                    throw new InvalidTransactionAmountException();
                default:
                    throw new UnrecognizedException();
            }
        }else {
            trans = new PaymentTransaction(response.get("errorCode").toString());
        }
        return trans;
    }
}
