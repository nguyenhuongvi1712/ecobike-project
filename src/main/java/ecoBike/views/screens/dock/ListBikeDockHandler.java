package ecoBike.views.screens.dock;

import ecoBike.controller.DockController;
import ecoBike.controller.ViewBikeController;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.dock.Dock;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.bike.BikeHandler;
import ecoBike.views.screens.bike.BikeInformationHandler;
import ecoBike.views.screens.home.DockHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListBikeDockHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private HBox hboxBike;

    @FXML
    private ImageView homeLogo;

    @FXML
    private Label dockName;

    @FXML
    private ImageView convertBtn;

    @FXML
    private TextField barcodeInput;

    @FXML
    private Label errMess;


    private Dock dock;

    List dockItems;

    private String barcode = barcodeInput.getText();

    private Rental rental;

    /**
     * Constructor , when the user is not using any bike
     * @param stage
     * @param screenPath
     * @param dock
     * @param homeScreenHandler
     * @throws IOException
     */

    public ListBikeDockHandler(Stage stage, String screenPath, Dock dock, BaseScreenHandler homeScreenHandler) throws IOException {
        super(stage, screenPath);
        this.dock = dock;
//        this.barcodeInput = null;
        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHome();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
        convertBtn.setOnMouseClicked(e->{
            try {
                requestToConvert();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        initDock(stage, this, this.rental);
    }

    /**
     * Constructor ,  when the user is using a rented bike
     * @param stage
     * @param screenPath
     * @param dock
     * @param homeScreenHandler
     * @param rental
     * @throws IOException
     */
    public ListBikeDockHandler(Stage stage, String screenPath, Dock dock, BaseScreenHandler homeScreenHandler, Rental rental) throws IOException {
        super(stage, screenPath);
        this.dock = dock;
        this.rental = rental;
//        this.barcodeInput = null;
        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHomeAfterRent(rental);
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
        convertBtn.setOnMouseClicked(e->{
            try {
                requestToConvert();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        initDock(stage, this, rental);
    }

    /**
     * init dock
     * @param stage
     * @param home
     * @param rental
     */
    public void initDock(Stage stage, ListBikeDockHandler home, Rental rental) {
        try {
            dockName.setText(dock.getName());
            List medium = ((DockController) getBController()).getAllBikeAvailable(dock.getId());
            this.dockItems = new ArrayList<>();
            for (Object object : medium) {
                Bike bike = (Bike) object;
                BikeHandler bikeHandler;
                if (rental == null) {
                    bikeHandler = new BikeHandler(stage, Configs.BIKE_STATION_PATH, bike, this);
                    this.homeLogo.setOnMouseClicked(e -> {
                        try {
                            backToHome();
                        } catch (IOException | SQLException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                } else {
                    this.homeLogo.setOnMouseClicked(e -> {
                        try {
                            backToHomeAfterRent(rental);
                        } catch (IOException | SQLException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    bikeHandler = new BikeHandler(stage, Configs.BIKE_STATION_PATH, bike, this, rental);
                }
                this.dockItems.add(bikeHandler);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        addBike(dockItems);
    }

    /**
     * Add list bike of Dock to dock screen
     * @param items
     */

    public void addBike(List items) {
        ArrayList dockItems = (ArrayList) ((ArrayList) items).clone();
        hboxBike.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });
        int rootSize = dockItems.size();
        if (!dockItems.isEmpty()) {
            int size = dockItems.size();
            hboxBike.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.setSpacing(20);
                while (vBox.getChildren().size() < Math.ceil((double)size/2) && !dockItems.isEmpty()) {
                    BikeHandler bike = (BikeHandler) dockItems.get(0);
                    vBox.getChildren().add(bike.getContent());
                    dockItems.remove(bike);
                }
            });
        }
        if(rootSize %2 != 0){
            VBox vBox = (VBox) hboxBike.getChildren().get(1);
            vBox.setSpacing(20);
            GridPane emptyPane = new GridPane();
            emptyPane.setPrefHeight(200.0);
            emptyPane.setPrefWidth(200.0);
            vBox.getChildren().add(emptyPane);
        }
    }

    /**
     * Move to this screen
     * @param prevScreen
     * @throws SQLException
     */

    public void requestToViewListBike(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        show();
    }

    /**
     * Request to find bike by barcode , move to bike screen if barcode is valid
     * @throws Exception
     */

    public void requestToConvert() throws Exception {
        try{
            ViewBikeController viewBikeController = new ViewBikeController();
            String barcode = this.barcodeInput.getText();
            Bike bike = viewBikeController.convertBarcode(barcode);
            BikeInformationHandler bikeScreen;
            try {
                if (this.rental == null) {
                    bikeScreen = new BikeInformationHandler(stage, Configs.BIKE_INFO_PATH);
                    bikeScreen.setBController(new ViewBikeController());
                    bikeScreen.requestToViewBike(this, bike.getId(), bike.getType());
                } else {
                    bikeScreen = new BikeInformationHandler(stage, Configs.BIKE_INFO_PATH, rental);
                    bikeScreen.setBController(new ViewBikeController());
                    bikeScreen.requestToViewBike(this, bike.getId(), bike.getType(), rental);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }catch (Exception e){
            notify(e.getMessage());
        }
    }

    /**
     * Display error when barcode is invalid or not exist
     * @param message
     */
    public void notify(String message) {
        LOGGER.info(message);
        errMess.setText(message);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBController(new DockController());
    }
}
