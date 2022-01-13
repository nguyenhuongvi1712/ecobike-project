package ecoBike.views.screens.payment;

import ecoBike.controller.PaymentController;
import ecoBike.controller.RentBikeController;
import ecoBike.controller.ViewBikeController;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.invoice.Invoice;
import ecoBike.entity.payment.CreditCard;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Utils;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentScreenHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private Label bikeId;
    @FXML
    private Label type;
    @FXML
    private Label dockName;
    @FXML
    private Label licensePlate;
    @FXML
    private Label barcode;
    @FXML
    private Label battery;
    @FXML
    private Label remainingTime;
    @FXML
    private Label value;
    @FXML
    private Label amount;
    @FXML
    private Label batteryLabel;
    @FXML
    private Label remainingTimeLabel;
    @FXML
    private Label errMess;

    @FXML
    private TextField owner;
    @FXML
    private TextField cardCode;
    @FXML
    private TextField dateExpired;
    @FXML
    private TextField cvvCode;
    @FXML
    private TextArea content;
    @FXML
    private ChoiceBox issuingBank;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;
    

    private CreditCard creditCard;
    private Invoice invoice;
    private Rental rental;

    /**
     * Constructor , when the user is not using any bike
     * @param stage
     * @param screenPath
     * @param rental
     * @throws IOException
     */
    public PaymentScreenHandler(Stage stage, String screenPath, Rental rental) throws IOException {
        super(stage, screenPath);
        this.rental = rental;
        this.creditCard = null;
        this.invoice = new Invoice();
        invoice.setRental(rental);
        cancelBtn.setOnMouseClicked(e -> {
            try {
                cancelRentBike();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        confirmBtn.setOnMouseClicked(e->{
            try {
                submitToPay();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    /**
     * Constructor ,  when the user is using a rented bike
     * @param stage
     * @param screenPath
     * @param invoice
     * @param card
     * @param rental
     * @throws IOException
     */

    public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice, CreditCard card, Rental rental) throws IOException {
        super(stage, screenPath);
        this.rental = rental;
        this.invoice = invoice;
        this.creditCard = card;
    }

    private void setCartInfo(CreditCard card) {
        this.cardCode.setText(card.getCardCode());
        this.owner.setText(card.getOwner());
        this.dateExpired.setText(card.getDateExpired());
    }

    /**
     * Set payment information
     */

    private void setPaymentInfo(){
        this.issuingBank.getItems().add("MB Bank (MBC)");
        this.issuingBank.getItems().add("Techcombank (TCB)");
        this.issuingBank.getItems().add("Vietcombank (VCB)");
        this.issuingBank.getSelectionModel().select(0);
        this.content.setText(((PaymentController) getBController()).getContent(rental.getRentalBike().getBarcode()));
    }

    /**
     * Set rent bike information
     */
    private void setRentBikeInfo(){
        this.batteryLabel.setVisible(false);
        this.remainingTimeLabel.setVisible(false);
        this.battery.setVisible(false);
        this.remainingTime.setVisible(false);

        Bike rentedBike = rental.getRentalBike();
        bikeId.setText("Bike "+ rentedBike.getId());
        type.setText(rentedBike.getType() + " bike");
        barcode.setText(rentedBike.getBarcode());
        dockName.setText(rentedBike.getDock().getName());
        licensePlate.setText(rentedBike.getLicensePlate());
        value.setText(Utils.getCurrencyFormat(((PaymentController) getBController()).getValue(rentedBike)));
        amount.setText(Utils.getCurrencyFormat(((PaymentController) getBController()).getDeposit(rentedBike)));
        if(rentedBike instanceof StandardEBike){
            this.batteryLabel.setVisible(true);
            this.remainingTimeLabel.setVisible(true);
            this.battery.setVisible(true);
            this.remainingTime.setVisible(true);
            this.battery.setText(((StandardEBike) rentedBike).getBattery() + "%");
            this.remainingTime.setText(Utils.convertTime(((StandardEBike) rentedBike).getRemainingTimeTs()));
        }
    }

    /**
     * Move to this screen
     * @param prev
     * @param homeScreenHandler
     */

    public void requestToRentBike(BaseScreenHandler prev, HomeScreenHandler homeScreenHandler) {
        setPreviousScreen(prev);
        setRentBikeInfo();
        setPaymentInfo();
        setHomeScreenHandler(homeScreenHandler);
        show();
    }

    /**
     * Move to previous screen when user click on cancel button
     * @throws IOException
     * @throws SQLException
     */

    private void cancelRentBike() throws IOException, SQLException {
        LOGGER.info("Cancel rentBike button clicked");
        RentBikeController rentBikeController = new RentBikeController();
        rentBikeController.deleteRental(rental);
        if (this.getPreviousScreen() instanceof HomeScreenHandler) {
            if (this.rental == null) backToHome();
//            else backToHomeAfterRent(this.order);
        } else {
            this.getPreviousScreen().show();
        }
    }

    /**
     * Request to pay
     * @throws Exception
     */

    private void submitToPay() throws Exception {
        try {
            invoice.setContents(content.getText());
            int amount = ((PaymentController) getBController()).getDeposit(rental.getRentalBike());
            invoice.setAmount(amount);
            getBController().processPayRequest(this.cardCode.getText(), this.owner.getText(), this.cvvCode.getText(), this.dateExpired.getText(), (String) this.issuingBank.getValue(), invoice, stage, homeScreenHandler, this);
        } catch (Exception e) {
            notify(e.getMessage());
        }
    }

    /**
     * Get controller of this screen
     * @return
     */
    public PaymentController getBController() {
        return (PaymentController) super.getBController();
    }

    /**
     * Display error message
     * @param message
     */

    public void notify(String message) {
        LOGGER.info(message);
        errMess.setText(message);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
