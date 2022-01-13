package ecoBike.views.screens.home;

import ecoBike.controller.PaymentController;
import ecoBike.controller.RentBikeController;
import ecoBike.controller.ReturnController;
import ecoBike.entity.dock.Dock;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.views.screens.FXMLScreenHandler;
import ecoBike.views.screens.dock.DockScreenHandler;
import ecoBike.views.screens.payment.PaymentScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

public class DockHandler extends FXMLScreenHandler {
    @FXML
    protected ImageView stationImage;

    @FXML
    protected Label dockName;

    @FXML
    protected Label numOfAvailableBikes;

    @FXML
    protected Label stationAvailableBike;

    @FXML
    protected Label maxCapacity;

    @FXML
    protected Button select;

    @FXML
    protected Button distanceBtn;

    private Dock dock;
    private HomeScreenHandler home;
    private Rental rental;

    /**
     * Constructor , when the user is not using any bike
     * @param screenPath
     * @param dock
     * @param home
     * @throws SQLException
     * @throws IOException
     */

    public DockHandler(String screenPath, Dock dock, HomeScreenHandler home) throws SQLException, IOException {
        super(screenPath);
        this.dock = dock;
        this.home = home;
        //Select dock marker
        initHomeStations(dock, home, this.rental);
        setDockInfo();
    }

    /**
     * Constructor ,   when the user is using a rented bike
     * @param screenPath
     * @param dock
     * @param home
     * @param rental
     * @throws SQLException
     * @throws IOException
     */

    public DockHandler(String screenPath, Dock dock, HomeScreenHandler home, Rental rental) throws SQLException, IOException {
        super(screenPath);
        this.dock = dock;
        this.home = home;
        this.rental = rental;
        initHomeStations(dock, home, rental);
        setDockInfo();
    }

    /**
     * init home dock
     * @param dock
     * @param home
     * @param rental
     */

    public void initHomeStations(Dock dock, HomeScreenHandler home, Rental rental) {
        if (rental != null)
            select.setText("Return");
        select.setOnMouseClicked(event -> {
            DockScreenHandler dockScreenHandler;
            try {
//                if (rental == null) {
//                    dockScreenHandler = new DockScreenHandler(home.getStage(), Configs.DOCK_SCREEN_PATH, dock, home);
//                    dockScreenHandler.setHomeScreenHandler(home);
//                    dockScreenHandler.requestToViewDock(home);
//                }
//                else {
//                    requestToReturnBike();
//                };
                if (rental == null)
                    dockScreenHandler = new DockScreenHandler(home.getStage(), Configs.DOCK_SCREEN_PATH, dock, home);
                else dockScreenHandler = new DockScreenHandler(home.getStage(), Configs.DOCK_SCREEN_PATH, dock, home, rental);
                dockScreenHandler.setHomeScreenHandler(home);
                dockScreenHandler.requestToViewDock(home);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        distanceBtn.setOnMouseClicked(event -> {
            try {
                System.out.println("Distance clicked");
                PopupDockScreen.showPopup(dock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set dock information
     * @throws SQLException
     */

    private void setDockInfo() throws SQLException {
        // set the cover image of station
        dockName.setText(dock.getName());
        maxCapacity.setText("Empty points: " + (dock.getMaxCapacity()));
        numOfAvailableBikes.setText("Available bikes: " + (dock.getNumOfAvailableBike()));
    }

}
