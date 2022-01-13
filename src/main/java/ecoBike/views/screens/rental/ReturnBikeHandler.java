package ecoBike.views.screens.rental;

import ecoBike.controller.BaseController;
import ecoBike.controller.PaymentController;
import ecoBike.controller.RentBikeController;
import ecoBike.controller.ReturnController;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.dock.Dock;
import ecoBike.entity.invoice.Invoice;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Utils;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnBikeHandler extends BaseScreenHandler {
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
    private Label deposit;
    @FXML
    private Label fee;
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
    private Rental rental;
    private Dock newDock ;
    private Invoice invoice;
    private ReturnController returnController;

    /**
     * Controller
     * @param stage
     * @param screenPath
     * @param bController
     * @param dock
     * @param rental
     * @throws IOException
     */
    public ReturnBikeHandler(Stage stage, String screenPath, BaseController bController, Dock dock, Rental rental) throws IOException {
        super(stage, screenPath);
//        setBController(bController);
        this.rental = rental;
        this.newDock = dock;

        this.invoice = new Invoice();
        invoice.setRental(rental);
        returnController = new ReturnController();

        //update rented bike's station
        this.rental.getRentalBike().setDock(newDock);
//        setBikeInfo();
//        setPaymentInfo();
        confirmBtn.setOnMouseClicked(e->{
            try {
                submitToPay();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        // cancel Btn rent
        cancelBtn.setOnMouseClicked(e -> {
            try {
                cancelRentBike();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Set payment information
     */

    private void setPaymentInfo(){
        this.issuingBank.getItems().add("MB Bank (MBC)");
        this.issuingBank.getItems().add("Techcombank (TCB)");
        this.issuingBank.getItems().add("Vietcombank (VCB)");
        this.issuingBank.getSelectionModel().select(0);
        this.content.setText(((PaymentController) getBController()).getContentReturn(rental.getRentalBike().getBarcode()));
    }

    /**
     * Set bike information
     */

    private void setBikeInfo(){
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
        deposit.setText(Utils.getCurrencyFormat(this.returnController.getDeposit(rental.getRentalBike())));
        fee.setText(Utils.getCurrencyFormat(rental.getTotalUpToNow()));
        amount.setText(Utils.getCurrencyFormat(this.returnController.calculateAmount(rental)));
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
     * get controller of this screen
     * @return
     */
    public PaymentController getBController() {
        return (PaymentController) super.getBController();
    }

    /**
     * Display error message when payment information is not valid
     * @param message
     */
    public void notify(String message) {
        LOGGER.info(message);
        errMess.setText(message);
    }

    /**
     * Request to pay
     * @throws Exception
     */
    private void submitToPay() throws Exception {
        try {
            invoice.setContents(content.getText());
            int amount = this.returnController.calculateAmount(rental);
            // set timeend ;
            rental.setTimeEnd(System.currentTimeMillis()/1000);
            invoice.setAmount(amount);
            invoice.setRental(rental);
            getBController().processPayRequest(this.cardCode.getText(), this.owner.getText(), this.cvvCode.getText(), this.dateExpired.getText(), (String) this.issuingBank.getValue(), invoice, stage, homeScreenHandler, this);
        } catch (Exception e) {
            notify(e.getMessage());
        }
    }

    /**
     * Move to this screen
     * @param prev
     * @param homeScreenHandler
     */

    public void requestToReturnBike(BaseScreenHandler prev, HomeScreenHandler homeScreenHandler) {
        setPreviousScreen(prev);
        setBikeInfo();
        setPaymentInfo();
        setHomeScreenHandler(homeScreenHandler);
        show();
    }

    /**
     * Move to previousScreen
     * @throws IOException
     * @throws SQLException
     */

    private void cancelRentBike() throws IOException, SQLException {
        LOGGER.info("Cancel returnBike button clicked");
        if (this.getPreviousScreen() instanceof HomeScreenHandler) {
            if (this.rental == null) backToHome();
//            else backToHomeAfterRent(this.order);
        } else {
            this.getPreviousScreen().show();
        }
    }
}
