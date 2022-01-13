package ecoBike.views.screens.bike;

import ecoBike.controller.PaymentController;
import ecoBike.controller.RentBikeController;
import ecoBike.controller.ViewBikeController;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.utils.Utils;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import ecoBike.views.screens.payment.PaymentScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class BikeInformationHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private ImageView homeLogo;
    @FXML
    private Button rentBikeBtn;
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
    private Label deposit;
    @FXML
    private Label batteryLabel;
    @FXML
    private Label remainingTimeLabel;
    @FXML
    private Label battery;
    @FXML
    private Label remainingTime;
    @FXML
    private Button cancelBtn;

    private Rental rental;
    private Bike bike;
    private static Logger LOGGER = Utils.getLogger(BikeInformationHandler.class.getName());
    private HomeScreenHandler homeScreenHandler;

    /**
     * Constructor
     * @param screenPath
     * @throws IOException
     */
    public BikeInformationHandler(String screenPath) throws IOException {
        super(screenPath);
        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHome();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
        this.cancelBtn.setOnMouseClicked((e -> {
            try {
                cancelViewBike();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }));
        this.rentBikeBtn.setOnMouseClicked(e-> {
            try {
                rentBike();
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });

    }

    /**
     * Constructor,when the user is not using any bike
     * @param stage
     * @param screenPath
     * @throws IOException
     */

    public BikeInformationHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHome();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
        this.cancelBtn.setOnMouseClicked((e -> {
            try {
                cancelViewBike();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }));
        this.rentBikeBtn.setOnMouseClicked(e-> {
            try {
                rentBike();
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Constructor , when the user is using a rented bike
     * @param stage
     * @param screenPath
     * @param rental
     * @throws IOException
     * @throws SQLException
     */

    public BikeInformationHandler(Stage stage, String screenPath, Rental rental) throws IOException, SQLException {
        super(stage, screenPath);
        this.rental = rental;
        this.cancelBtn.setOnMouseClicked((e -> {
            try {
                cancelViewBike();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }));
    }

    /**
     * Move to this screen when the user is using a rented bike
     * @param prevScreen
     * @param id
     * @param type
     * @param rental
     * @throws SQLException
     * @throws IOException
     */

    public void requestToViewBike(BaseScreenHandler prevScreen, long id, String type, Rental rental) throws SQLException, IOException {
        this.setPreviousScreen(prevScreen);
        setBikeInfo(id, type);
        setStatus(false);
        this.rental = rental;
        show();
    }

    /**
     * Move to this screen when thi user is not using any bike
     * @param prevScreen
     * @param id
     * @param type
     * @throws SQLException
     * @throws IOException
     */

    public void requestToViewBike(BaseScreenHandler prevScreen, long id, String type) throws SQLException, IOException {
        this.setPreviousScreen(prevScreen);
        setBikeInfo(id, type);
        setStatus(true);
        this.rental = null;
        show();
    }

    /**
     * Set bike infor
     * @param id
     * @param type
     * @throws IOException
     * @throws SQLException
     */

    public void setBikeInfo(long id, String type) throws IOException, SQLException {
        this.batteryLabel.setVisible(false);
        this.remainingTimeLabel.setVisible(false);
        this.battery.setVisible(false);
        this.remainingTime.setVisible(false);

        this.bike = ((ViewBikeController) getBController()).setBike(id, type);
        bikeId.setText("Bike "+bike.getId());
        this.type.setText(bike.getType() + " bike");
        barcode.setText(bike.getBarcode());
        dockName.setText(bike.getDock().getName());
        licensePlate.setText(bike.getLicensePlate());
        deposit.setText(Utils.getCurrencyFormat(((ViewBikeController) getBController()).getDeposit(bike)));
        if(bike instanceof StandardEBike){
            this.batteryLabel.setVisible(true);
            this.remainingTimeLabel.setVisible(true);
            this.battery.setVisible(true);
            this.remainingTime.setVisible(true);
            this.battery.setText(((StandardEBike) bike).getBattery() + "%");
            this.remainingTime.setText(Utils.convertTime(((StandardEBike) bike).getRemainingTimeTs()));
        }

        // set image from url
//        setImage(urlImage, bike.getUrlImage());

    }

    /**
     * can't rent bike when we are renting another bike or this bike is renting, then the button is set to disable
     * @param status
     * @throws SQLException
     */

    public void setStatus(Boolean status) throws SQLException { //status = true => can rent
        if(status == false)
            rentBikeBtn.setDisable(true);
        else if (new RentBikeController().checkAvailableBike(bike) == false)
            rentBikeBtn.setDisable(true);
    }
    private void cancelViewBike() throws IOException, SQLException {
        LOGGER.info("Cancel button clicked");
        if (this.getPreviousScreen() instanceof HomeScreenHandler) {
            if (this.rental == null) backToHome();
//            else backToHomeAfterRent(this.order);
        } else {
            this.getPreviousScreen().show();

        }
    }

    /**
     * Move to payment screen
     * @throws IOException
     * @throws SQLException
     */

    private void rentBike() throws IOException, SQLException {
        LOGGER.info("Rent bike button clicked");
        RentBikeController rentBikeController = new RentBikeController();
        Rental rental = rentBikeController.createRental(bike);
        PaymentScreenHandler payment = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, rental);
        payment.setBController(new PaymentController());
        payment.requestToRentBike(this, homeScreenHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
