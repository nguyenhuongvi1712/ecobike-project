package ecoBike.controller;

import ecoBike.common.exception.InvalidCardException;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardBike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.bike.TwinBike;
import ecoBike.entity.dock.Dock;
import ecoBike.entity.invoice.Invoice;
import ecoBike.entity.payment.CreditCard;
import ecoBike.entity.payment.PaymentTransaction;
import ecoBike.entity.rental.Rental;
import ecoBike.repository.Payment.PaymentTransactionRepository;
import ecoBike.repository.bike.BikeReponsitory;
import ecoBike.repository.dock.DockRepository;
import ecoBike.repository.invoice.InvoiceRepository;
import ecoBike.repository.rental.RentalRepository;
import ecoBike.subsystem.InterbankInterface;
import ecoBike.subsystem.interbank.InterbankSubsystemController;
import ecoBike.utils.Configs;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import ecoBike.views.screens.payment.ResultScreenHandler;
import ecoBike.views.screens.payment.TransactionErrorScreenHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PaymentController extends BaseController{
    private CreditCard card;
    private RentalRepository rentalRepository;
    private InvoiceRepository invoiceRepository;
    private PaymentTransactionRepository paymentTransactionRepository;
    private BikeReponsitory bikeReponsitory;
    private DockRepository dockRepository;

    /**
     *
     * @throws SQLException
     */
    public PaymentController() throws SQLException {
        rentalRepository = new RentalRepository();
        invoiceRepository = new InvoiceRepository();
        paymentTransactionRepository = new PaymentTransactionRepository();
        bikeReponsitory = new BikeReponsitory();
        dockRepository = new DockRepository();

    }
    /**
     * Represent the Interbank subsystem
     */
    private InterbankInterface interbank;
    private double amount;
    private String content;

    /**
     * Return default content of invoice when pay deposit for renting bike
     * @param barcode
     * @return
     */
    public String getContent(String barcode) {
        return "Pay deposit for renting bike " + barcode;
    }

    /**
     * Return default content of invoice when refund or pay for return bike
     * @param barcode
     * @return
     */
    public String getContentReturn (String barcode){
        return "Refund or pay for turning bike " + barcode;
    }

    /**
     * Get value of Bike
     * @param bike
     * @return
     */
    public int getValue (Bike bike){
        if(bike instanceof StandardBike)
            return StandardBike.VALUE ;
        else if(bike instanceof StandardEBike)
            return StandardEBike.VALUE;
        else if(bike instanceof TwinBike)
            return TwinBike.VALUE;
        else
            return 0;
    }

    /**
     * Get deposit of bike when renting bike
     * @param bike
     * @return
     */
    public int getDeposit(Bike bike) {
        if(bike instanceof StandardBike)
            return (int) (StandardBike.VALUE * 0.4);
        else if(bike instanceof StandardEBike)
            return (int) (StandardEBike.VALUE * 0.4);
        else if(bike instanceof TwinBike)
            return (int) (TwinBike.VALUE * 0.4);
        else
            return 0;
    }

    /**
     * Check validate cardcode
     * @return
     */
    public boolean validateCardCode(String number) {
        try {
            number = number.trim();
            return ((!number.equals("")) && (number.matches("^[_0-9A-Za-z]*$")));
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    /**
     * Check validate Expiration date of Credit Card
     * @param date
     * @return
     * @throws InvalidCardException
     */
    public boolean validateExpirationDate(String date) throws InvalidCardException {
        try {
            if (date == null) return false;
            date = date.trim();
            String regex = "^[0-9]{4}$";
            if (!date.matches(regex)) return false;
            return true;
        } catch (Exception e) {
            throw new InvalidCardException("Invalid expiration date");
        }
    }

    /**
     * Check validate card's holder name
     * @param name
     * @return
     */

    public boolean validateOwnerName(String name) {
        try {
            name = name.trim();
            return ((!name.equals("")) && (name.matches("^[ A-Za-z0-9]+$")));
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Check validate CvvCode
     * @param number
     * @return
     */
    public boolean validateCvvCode(String number) {

        try {
            number = number.trim();
            Integer.parseInt(number);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Validate card information when pay or refund
     * @param cardNumber
     * @param holderName
     * @param securityCode
     * @param expirationDate
     * @throws Exception
     */

    public void validateCardInfo(String cardNumber, String holderName, String securityCode, String expirationDate) throws Exception {
        if (!validateExpirationDate(expirationDate)) throw new InvalidCardException("Invalid expirationDate");
        if (!this.validateOwnerName(holderName))
            throw new InvalidCardException("Invalid Owner Name");
        else if (!this.validateCvvCode(securityCode))
            throw new InvalidCardException("Wrong format cvvCode");
        else if (!this.validateCardCode(cardNumber))
            throw new InvalidCardException("Wrong format code number");
    }

    /**
     * Control the process of paying deposit, pay/refund when  renting / returning bike
     * @param cardNumber
     * @param holderName
     * @param securityCode
     * @param expirationDate
     * @param issuingBank
     * @param invoice
     * @param stage
     * @param homeScreenHandler
     * @param prev
     * @throws Exception
     */
    public void processPayRequest(String cardNumber, String holderName, String securityCode, String expirationDate,String issuingBank, Invoice invoice, Stage stage, HomeScreenHandler homeScreenHandler, BaseScreenHandler prev) throws Exception {

        validateCardInfo(cardNumber, holderName, securityCode, expirationDate);
        CreditCard card = new CreditCard( cardNumber, holderName, securityCode, expirationDate,issuingBank);
        PaymentTransaction transactionResult = submitToPay(invoice, card);
        proceedTransactionResult(transactionResult, invoice, card, stage, homeScreenHandler, prev);
    }

    /**
     * Control the process after paying deposit, pay/refund when renting / returning bike
     * @param transactionResult
     * @param invoice
     * @param card
     * @param stage
     * @param homeScreenHandler
     * @param prev
     * @throws IOException
     * @throws SQLException
     */

    public void proceedTransactionResult(PaymentTransaction transactionResult, Invoice invoice, CreditCard card, Stage stage, HomeScreenHandler homeScreenHandler, BaseScreenHandler prev) throws IOException, SQLException {
        if (!transactionResult.getErrorCode().equals("00")) {
            displayTransactionError(transactionResult.getErrorCode(), stage, homeScreenHandler, prev);
        } else {
            Rental rental = updateRental(invoice.getRental());
            invoice = createInvoice(invoice);
            createNewTransaction(transactionResult,invoice);

            // update Bike and dock
            if(rental.getTimeEnd() !=0){
                bikeReponsitory.updateBikeAfterReturn(rental.getRentalBike(),rental.getRentalBike().getDock().getId());
                Dock dock = rental.getRentalBike().getDock();
                dockRepository.updateDock(dock.getId(),dock.getMaxCapacity()-1 , dock.getNumOfAvailableBike()+1);
                moveToPaymentResultAfterReturn(stage,invoice,transactionResult);
            }else{
                // update Dock ;
                Dock dock = rental.getRentalBike().getDock();
                System.out.println("update DOck id " );
                dockRepository.updateDock(dock.getId(),dock.getMaxCapacity()+1 , dock.getNumOfAvailableBike()-1);
                moveToPaymentResultAfterRent(stage, invoice,transactionResult);
            }
        }
    }

    /**
     * Move to payment result screen after return bike
     * @param stage
     * @param invoice
     * @param transactionResult
     * @throws IOException
     */
    public void moveToPaymentResultAfterReturn (Stage stage, Invoice invoice, PaymentTransaction transactionResult) throws IOException {
        ResultScreenHandler resultScreenHandler = new ResultScreenHandler(stage, Configs.PAYMENT_RESULT_SCREEN_PATH, transactionResult);
        resultScreenHandler.show();
    }

    /**
     * Move to payment result screen after rent bike
     * @param stage
     * @param invoice
     * @param transactionResult
     * @throws IOException
     */
    public void moveToPaymentResultAfterRent(Stage stage, Invoice invoice, PaymentTransaction transactionResult) throws IOException {

        ResultScreenHandler resultScreenHandler = new ResultScreenHandler(stage, Configs.PAYMENT_RESULT_SCREEN_PATH, transactionResult,invoice.getRental());
        resultScreenHandler.show();
    }

    /**
     * move to transaction error screen when transaction failed
     * @param errorCode
     * @param stage
     * @param homeScreenHandler
     * @param prev
     * @throws IOException
     */
    public void displayTransactionError(String errorCode, Stage stage, HomeScreenHandler homeScreenHandler, BaseScreenHandler prev) throws IOException {
        String errorMessage;
        errorMessage = Configs.errorCodes.get(errorCode);
        TransactionErrorScreenHandler transactionErrorScreenHandler = new TransactionErrorScreenHandler(stage, Configs.TRANSACTION_ERROR_SCREEN_PATH, errorMessage);
        transactionErrorScreenHandler.setPreviousScreen(prev);
        transactionErrorScreenHandler.show();
    }

    /**
     *
     * @param invoice
     * @param card
     * @return
     */

    public PaymentTransaction submitToPay(Invoice invoice, CreditCard card){
        InterbankSubsystemController interbank = new InterbankSubsystemController();
        PaymentTransaction transactionResult = null;
        if (invoice.getAmount() < 0) {
            transactionResult = interbank.refund(card, Math.abs(invoice.getAmount()), invoice.getContents());
        } else
            transactionResult = interbank.payOrder(card, invoice.getAmount(), invoice.getContents());
        return transactionResult;
    }

    /**
     * Update rental after rent or return bike
     * @param rental
     * @return
     * @throws SQLException
     */
    private Rental updateRental(Rental rental) throws SQLException {
        if(rental.getTimeEnd() != 0 ){
            rentalRepository.updateTotal(rental);
            return rental;
        }else{
            long ts = System.currentTimeMillis()/1000;
            rental.setTimeStart(ts);
            rental.setTotalUpToNow(0);
            return rentalRepository.updateTimeStart(rental);
        }
    }

    /**
     * create new invoice when rent or return bike
     * @param invoice
     * @return
     * @throws SQLException
     */
    private Invoice createInvoice(Invoice invoice) throws SQLException {
        return invoiceRepository.createNewInvoiceDB(invoice.getAmount(),invoice.getContents(),invoice.getRental());
    }

    /**
     * create new transaction when rent or return bike
     * @param paymentTransaction
     * @param invoice
     * @throws SQLException
     */

    private void createNewTransaction (PaymentTransaction paymentTransaction,Invoice invoice) throws SQLException {
        CreditCard card = paymentTransaction.getCard();
        long id = paymentTransactionRepository.createNewTransaction(paymentTransaction.getAmount(), paymentTransaction.getTransactionContent(), invoice.getId(),card.getCardCode(),card.getOwner(),card.getDateExpired(),paymentTransaction.getCreatedAt(),paymentTransaction.getTransactionId());
    }
}
