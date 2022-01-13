package ecoBike.views.screens.rental;

import ecoBike.controller.ReturnController;
import ecoBike.controller.ViewBikeController;
import ecoBike.entity.bike.Bike;
import ecoBike.entity.bike.StandardEBike;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.utils.Utils;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RentingBikeScreenHandler extends BaseScreenHandler implements Initializable {
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
    private ImageView pauseBtn;
    @FXML
    private Text timeCount;
    @FXML
    private Text amountCount;
    @FXML
    private Button returnBikeBtn;


    private Integer startAt ;
    private Timeline animation;
    private Bike bike;
    private Rental rental;
    private boolean flag;

    /**
     * Constructor
     * @param stage
     * @param screenPath
     * @param rental
     * @throws IOException
     */

    public RentingBikeScreenHandler(Stage stage, String screenPath, Rental rental) throws IOException {
        super(stage, screenPath);
        this.bike = rental.getRentalBike();
        this.rental= rental;
        homeLogo.setOnMouseClicked(event -> {
            try {
//                backToHomeAfterRent(rental);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        pauseBtn.setOnMouseClicked(event -> {
            try {
                toggleCountTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        returnBikeBtn.setOnMouseClicked(event -> {
            try {
                requestToReturnBike ();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * get controller of this screen
     * @return
     */

    public ViewBikeController getBController() {
        return (ViewBikeController) super.getBController();
    }

    /**
     * Get animation for counting time
     * @return
     */

    public Timeline getAnimation() {
        return animation;
    }

    /**
     * Set bike information
     * @throws IOException
     */
    public void setBikeInfo() throws IOException {
        ReturnController returnController = new ReturnController();
        this.batteryLabel.setVisible(false);
        this.remainingTimeLabel.setVisible(false);
        this.battery.setVisible(false);
        this.remainingTime.setVisible(false);

        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(1), ev -> {
                    handlerCountTime(returnController);
                }));
        animation.setCycleCount(Animation.INDEFINITE);
        this.animation = animation;
        this.getAnimation().play();

        this.bike = ((ViewBikeController) getBController()).setBike(bike.getId(), bike.getType());
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
    }

    /**
     * set start time
     */

    public void setStartAt() {
        this.startAt = getBController().calculateTimeToNow((int) rental.getTimeStart());
    }

    /**
     * Move to this screen
     * @param prevScreen
     * @throws SQLException
     * @throws IOException
     */
    public void requestToViewRentingBike(BaseScreenHandler prevScreen) throws SQLException, IOException {
        setStartAt();
        setBikeInfo();
        setPreviousScreen(prevScreen);
        show();
    }

    /**
     * handler of counting time
     * @param returnController
     */
    private void handlerCountTime(ReturnController returnController){
        this.timeCount.setText(Utils.convertTimeSecond(this.startAt));
        this.amountCount.setText(Utils.getCurrencyFormat(returnController.getAmount(rental.getRentalBike(),this.startAt)));
        startAt ++;
    }

    /**
     * toggle count time (pause / start )
     * @throws IOException
     */

    private void toggleCountTime() throws IOException {
        if (animation != null) {
            if (!isFlag()) {
                animation.pause();
                setImage(pauseBtn,"/ecoBike/views/images/start-icon.png");
                setFlag(true);

            } else {
                animation.play();
                setImage(pauseBtn,"/ecoBike/views/images/pause-icon.png");
                setFlag(false);
            }
        }
    }

    /**
     * return status flag of control pause/ start time
     * @return
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * set status flag of control pause/ start time
     * @param flag
     */

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * Move to home screen to select dock for return bike
     * @throws SQLException
     * @throws IOException
     */

    private void requestToReturnBike() throws SQLException, IOException {
        ReturnController returnController = new ReturnController();
        this.rental.setTotalUpToNow(returnController.getAmount(rental.getRentalBike(),this.startAt));
        System.out.println("total Up to now : " + this.rental.getTotalUpToNow());
        HomeScreenHandler homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH,rental);
        homeHandler.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
