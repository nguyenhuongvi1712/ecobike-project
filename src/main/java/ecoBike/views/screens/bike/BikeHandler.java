package ecoBike.views.screens.bike;

import ecoBike.controller.ViewBikeController;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.utils.Utils;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.dock.ListBikeDockHandler;
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

public class BikeHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private Button viewBike;

    @FXML
    private ImageView bikeImage;

    @FXML
    private Label bikeId;

    @FXML
    private Label bikeType;

    @FXML
    private Label bikeLicensePlate;

    @FXML
    private Label barcode;

    private static Logger LOGGER = Utils.getLogger(BikeHandler.class.getName());
    private Bike bike;
    private ListBikeDockHandler home;
    private Rental rental;

    /**
     * constructor
     *
     * @param stage
     * @param screenPath
     * @throws IOException
     */

    /**
     * Constructor, when user is not using any bike
     * @param stage
     * @param screenPath
     * @param bike
     * @param home
     * @throws IOException
     * @throws SQLException
     */
    public BikeHandler(Stage stage, String screenPath, Bike bike, ListBikeDockHandler home) throws IOException, SQLException {
        super(screenPath);
        this.bike = bike;
        this.home = (ListBikeDockHandler) home;
        initStationBikes(stage, home, bike, this.rental);
    }

    /**
     * Constructor , when user is using a rented bike
     * @param stage
     * @param screenPath
     * @param bike
     * @param home
     * @param rental
     * @throws IOException
     * @throws SQLException
     */

    public BikeHandler(Stage stage, String screenPath, Bike bike, ListBikeDockHandler home, Rental rental) throws IOException, SQLException {
        super(screenPath);
        this.bike = bike;
        this.home = (ListBikeDockHandler) home;
        this.rental = rental;
        initStationBikes(stage, home, bike, this.rental);
    }

    /**
     * Init station bikes
     * @param stage
     * @param home
     * @param bike
     * @param rental
     * @throws SQLException
     */
    public void initStationBikes(Stage stage, BaseScreenHandler home, Bike bike, Rental rental) throws SQLException {
        setBikeInfo();
        viewBike.setOnMouseClicked(e -> {
            BikeInformationHandler bikeScreen;
            try {
                if (rental == null) {
                    bikeScreen = new BikeInformationHandler(stage, Configs.BIKE_INFO_PATH);
                    bikeScreen.setBController(new ViewBikeController());
                    bikeScreen.requestToViewBike(home, bike.getId(), bike.getType());
                } else {
                    bikeScreen = new BikeInformationHandler(stage, Configs.BIKE_INFO_PATH, rental);
                    bikeScreen.setBController(new ViewBikeController());
                    bikeScreen.requestToViewBike(home, bike.getId(), bike.getType(), rental);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Set bike information
     */

    private void setBikeInfo() {
//        setImage(bikeImage, bike.getImageUrl());
        bikeId.setText("Bike: "+bike.getId());
        bikeType.setText(bike.getType());
        barcode.setText(bike.getBarcode());
        bikeLicensePlate.setText(bike.getLicensePlate());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
