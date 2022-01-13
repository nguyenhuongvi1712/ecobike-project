package ecoBike.repository.Payment;

import ecoBike.entity.payment.PaymentTransaction;
import ecoBike.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentTransactionRepository extends Repository {

    public PaymentTransactionRepository() throws SQLException {
    }
    // return id of new Transaction;
    public long createNewTransaction(int amount, String content, long invoiceId, String cardCode , String cardHolder , String expDate , String createdAt , String transactionId) throws SQLException {
        content = "\""+ content + "\"";
        cardHolder = "\"" + cardHolder + "\"";
        cardCode = "\"" + cardCode + "\"";
        expDate = "\"" + expDate + "\"";
        createdAt = "\"" + createdAt + "\"";
        transactionId = "\"" + transactionId + "\"";
        int affectedRows = stm.executeUpdate("INSERT INTO Transactions(amount,contents,invoiceId,cardCode,cardHolder,expDate,createdAt,transactionId) VALUES (" + amount + ","+content+","+invoiceId+","+cardCode+","+cardHolder+","+expDate+","+createdAt+","+transactionId+ ");", Statement.RETURN_GENERATED_KEYS);
        ResultSet keys = stm.getGeneratedKeys();
        if(keys.next())
            return keys.getLong(1);
        else return 0;
    }
}
