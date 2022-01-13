package ecoBike.views.screens.home;

import ecoBike.entity.dock.Dock;
import ecoBike.utils.Configs;
import ecoBike.views.screens.BaseScreenHandler;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class PopupDockScreen extends BaseScreenHandler {

    @FXML
    private Label area;

    @FXML
    private Label dockName;

    @FXML
    private Label numOfAvailableBikes;

    @FXML
    private Label maxCapacity;

    @FXML
    private Label numOfPoint;

    @FXML
    private Label address;

    @FXML
    /**
     * close the pop-up and back to home screen
     * @throws IOException
     * @throws SQLException
     */
    private void back() throws IOException, SQLException {
        LOGGER.info("Back button clicked");
        close(0);
    }

    /**
     * Constructor popup
     * @param stage
     * @param dock
     * @throws IOException
     */

    public PopupDockScreen(Stage stage,Dock dock) throws IOException{
        super(stage, Configs.POPUP_HOME_PATH);
        this.address.setText("Address: " + dock.getAddress());
        this.dockName.setText(dock.getName());
        this.numOfAvailableBikes.setText("Available bikes: " + dock.getNumOfAvailableBike());
        this.maxCapacity.setText("Empty points: " + dock.getMaxCapacity());
        this.numOfPoint.setText("Number of points: " + dock.getNumOfPoint());
        this.area.setText("Area: " + dock.getArea());
    }
    /**
     * constructor
     *
     * @param stage
     * @param screenPath
     * @throws IOException
     */
    public PopupDockScreen(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * style the pop-up
     * @param undecorated
     * @return
     * @throws IOException
     */
    private static PopupDockScreen popup(Boolean undecorated, Dock dock) throws IOException{
        PopupDockScreen popup = new PopupDockScreen(new Stage(), dock);
        if (undecorated) popup.stage.initStyle(StageStyle.UNDECORATED);
        return popup;
    }

    /**
     * show pop-up
     * @throws IOException
     */
    public static void showPopup(Dock dock) throws IOException{
        popup(true,dock).show(false);
    }

    /**
     * show and auto close after sometime
     * @param autoclose
     */
    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    /**
     * show and close after sometime
     * @param time
     */
    public void show(double time) {
        super.show();
        close(time);
    }

    /**
     * close the pop-up after sometime
     * @param time
     */
    public void close(double time){
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }
}
