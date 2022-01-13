package ecoBike.repository.invoice;

import ecoBike.entity.invoice.Invoice;
import ecoBike.entity.rental.Rental;
import ecoBike.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InvoiceRepository extends Repository {
    public InvoiceRepository() throws SQLException {
    }

    public Invoice createNewInvoiceDB(int amount , String content , Rental rental) throws SQLException {
        content = "\"" + content + "\"";
        stm.executeUpdate("INSERT INTO Invoices(contents, amount, rentalId) VALUES (" + content + "," + amount + "," + rental.getId() + ");", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stm.getGeneratedKeys();
        Invoice invoice = new Invoice();
        if(rs.next()){
            invoice.setId(rs.getLong(1));
        }
        invoice.setAmount(amount);
        invoice.setContents(content);
        invoice.setRental(rental);
        return invoice;
    }
}
