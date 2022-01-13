package ecoBike.views.screens.payment;

import ecoBike.views.screens.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionErrorScreenHandler extends BaseScreenHandler {
    @FXML
    private Label errorMess ;
    @FXML
    private Button confirmBtn;

    /**
     * Constructor , when transaction failed
     * @param stage
     * @param screenPath
     * @param errorMessage
     * @throws IOException
     */
    public TransactionErrorScreenHandler(Stage stage, String screenPath, String errorMessage) throws IOException {
        super(stage, screenPath);
        errorMess.setText(errorMessage);
        confirmBtn.setOnMouseClicked(e->{
            try {
                moveToPaymentScreen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Move to payment screen
     * @throws IOException
     */

    private  void moveToPaymentScreen() throws IOException {
        this.getPreviousScreen().show();
    }
}
