package ecoBike.views.screens.dock;

import ecoBike.controller.DockController;
import ecoBike.controller.PaymentController;
import ecoBike.controller.ReturnController;
import ecoBike.entity.dock.Dock;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import ecoBike.views.screens.rental.ReturnBikeHandler;
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

public class DockScreenHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private Label dockName;

    @FXML
    private Label dockAddress;

    @FXML
    private Label area;

    @FXML
    private Label numOfAvailableBike;

    @FXML
    private Label maxCapacity;

    @FXML
    private Label numOfPoints;

    @FXML
    private Button returnBtn;

    @FXML
    private ImageView homeLogo;

    @FXML
    private Button showListBikeBtn;

    private Dock dock;
    private Rental rental;

    /**
     * Constructor when user is not using any bike
     * @param stage
     * @param screenPath
     * @param dock
     * @param homeScreenHandler
     * @throws IOException
     */
    public DockScreenHandler(Stage stage, String screenPath, Dock dock, BaseScreenHandler homeScreenHandler) throws IOException {
        super(stage, screenPath);
        this.dock= dock;
        returnBtn.setVisible(false);

        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHome();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });

        initDock(stage, this, this.rental);
    }

    /**
     * Constructor , when the user is using a rented bike
     * @param stage
     * @param screenPath
     * @param dock
     * @param homeScreenHandler
     * @param rental
     * @throws IOException
     */
    public DockScreenHandler(Stage stage, String screenPath, Dock dock, BaseScreenHandler homeScreenHandler, Rental rental) throws IOException {
        super(stage, screenPath);
        this.dock = dock;
        this.rental = rental;

        this.returnBtn.setOnMouseClicked(e -> {
            try {
                ReturnBikeHandler returnBikeHandler = new ReturnBikeHandler(stage, Configs.RETURN_BIKE_SCREEN_PATH, new ReturnController(), this.dock, rental);
                returnBikeHandler.setBController(new PaymentController());
                returnBikeHandler.requestToReturnBike((BaseScreenHandler) this, (HomeScreenHandler) homeScreenHandler);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHomeAfterRent(rental);
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
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
    public void initDock(Stage stage, DockScreenHandler home, Rental rental) {
        setDockInfo();
        this.showListBikeBtn.setOnMouseClicked(e -> {
            ListBikeDockHandler listBikeDockHandler;
            try {
                if (rental == null)
                    listBikeDockHandler = new ListBikeDockHandler(home.getStage(), Configs.DOCK_DETAIL_SCREEN_PATH, dock, home);
                else listBikeDockHandler = new ListBikeDockHandler(home.getStage(), Configs.DOCK_DETAIL_SCREEN_PATH, dock, home, rental);
//                listBikeDockHandler.setHomeScreenHandler(home);
                listBikeDockHandler.setBController(new DockController());
                listBikeDockHandler.requestToViewListBike(home);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

    }

    /**
     * Set information
     */

    private void setDockInfo() {
        this.dockName.setText(dock.getName());
        this.dockAddress.setText(dock.getAddress());
        this.area.setText(""+dock.getArea());
        this.maxCapacity.setText(""+dock.getMaxCapacity());
        this.numOfAvailableBike.setText((""+dock.getNumOfAvailableBike()));
        this.numOfPoints.setText(""+dock.getNumOfPoint());
    }

    /**
     * request to move to this screen
     * @param prevScreen
     * @throws SQLException
     */
    public void requestToViewDock(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        show();
    }

    /**
     * get stage of this screen
     * @return
     */

    public Stage getStage() {
        return this.stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
