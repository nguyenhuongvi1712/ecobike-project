package ecoBike.entity.payment;

public class CreditCard {
    private String cardCode;
    private String owner;
    private String  cvvCode;
    private String dateExpired;
    private String issuingBank;

    public CreditCard(String cardCode, String owner, String cvvCode, String dateExpired) {
        this.cardCode = cardCode;
        this.owner = owner;
        this.cvvCode = cvvCode;
        this.dateExpired = dateExpired;
    }

    public CreditCard(String cardCode, String owner, String cvvCode, String dateExpired, String issuingBank) {
        this.cardCode = cardCode;
        this.owner = owner;
        this.cvvCode = cvvCode;
        this.dateExpired = dateExpired;
        this.issuingBank = issuingBank;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getOwner() {
        return owner;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public String getIssuingBank() {
        return issuingBank;
    }
}
