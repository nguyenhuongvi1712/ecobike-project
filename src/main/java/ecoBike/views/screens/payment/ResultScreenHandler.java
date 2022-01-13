package ecoBike.views.screens.payment;

import ecoBike.controller.BaseController;
import ecoBike.controller.ViewBikeController;
import ecoBike.entity.payment.PaymentTransaction;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.utils.Utils;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.home.HomeScreenHandler;
import ecoBike.views.screens.rental.RentingBikeScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResultScreenHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private Text holderName;
    @FXML
    private Text cardCode;
    @FXML
    private Text expDate;
    @FXML
    private Text content;
    @FXML
    private Text amount;
    @FXML
    private Button btn;
    @FXML
    private ImageView homeLogo;

    private Rental rental;

    /**
     * Constructor , when the user is not using any bike
     * @param stage
     * @param screenPath
     * @param trans
     * @throws IOException
     */
    public ResultScreenHandler(Stage stage, String screenPath, PaymentTransaction trans) throws IOException {
        super(stage, screenPath);
        setTransactionInfo(trans);
        btn.setVisible(false);
        homeLogo.setOnMouseClicked(e->{
            try {
                backToHome();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * Constructor , when the user is using a rented bike
     * @param stage
     * @param screenPath
     * @param trans
     * @param rental
     * @throws IOException
     */
    public ResultScreenHandler(Stage stage, String screenPath, PaymentTransaction trans, Rental rental) throws IOException {
        super(stage, screenPath);
        setTransactionInfo(trans);
        this.rental = rental;
        btn.setOnMouseClicked(e->{
            try {
                requestToViewRentingBike();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Set transaction information of this screen
     * @param trans
     */

    public void setTransactionInfo(PaymentTransaction trans) {
        this.cardCode.setText(trans.getCard().getCardCode());
        this.expDate.setText(trans.getCard().getDateExpired());
        this.holderName.setText(trans.getCard().getOwner());
        this.content.setText(trans.getTransactionContent());
        this.amount.setText(Utils.getCurrencyFormat(trans.getAmount()));
    }

    /**
     * Move to renting bike screen
     * @throws SQLException
     * @throws IOException
     */

    private void requestToViewRentingBike() throws SQLException, IOException {
        RentingBikeScreenHandler viewRentingBike = new RentingBikeScreenHandler(this.stage, Configs.RENT_BIKE_INFO_PATH, rental);
        viewRentingBike.setBController(new ViewBikeController());
        viewRentingBike.requestToViewRentingBike(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
