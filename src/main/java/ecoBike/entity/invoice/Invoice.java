package ecoBike.entity.invoice;

import ecoBike.entity.rental.Rental;

public class Invoice {
    private long id;
    private Rental rental;
    private int amount ;
    private String contents;

    public Invoice(){};

    public Invoice(long id, Rental rental, int amount, String contents) {
        this.id = id;
        this.rental = rental;
        this.amount = amount;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
