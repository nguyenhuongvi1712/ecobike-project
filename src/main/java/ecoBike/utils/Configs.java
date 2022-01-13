package ecoBike.utils;

import java.util.HashMap;
import java.util.Map;

public class Configs {

    // api constants
    public static final String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";
    public static final String APP_CODE = "AyXz5ZjXTJI=";
    public static final String SECRET_KEY = "Bs3tHBV1CuI=";
    public static final String PAY_CMD = "pay";
    public static final String VERSION = "1.0.1";
    public static final String REFUND_CMD = "refund";



    // database Configs
    public static final String DB_URL = "jdbc:mysql://localhost:3306/ecobike";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "vianh1712";

    // static resource
    public static final String DOCK_DETAIL_SCREEN_PATH = "/ecoBike/views/fxml/dock-detail.fxml";
    public static final String DOCK_SCREEN_PATH = "/ecoBike/views/fxml/dock.fxml";
    public static final String PAYMENT_RESULT_SCREEN_PATH = "/ecoBike/views/fxml/payment-result.fxml";
    public static final String PAYMENT_SCREEN_PATH = "/ecoBike/views/fxml/payment.fxml";
    public static final String SPLASH_SCREEN_PATH = "/ecoBike/views/fxml/splash-v2.fxml";
    public static final String HOME_PATH = "/ecoBike/views/fxml/home-page.fxml";
    public static final String DOCK_HOME_PATH = "/ecoBike/views/fxml/dock-home.fxml";
    public static final String POPUP_HOME_PATH = "/ecoBike/views/fxml/popup-dock.fxml";
    public static final String BIKE_STATION_PATH = "/ecoBike/views/fxml/bike.fxml";
    public static final String TRANSACTION_ERROR_SCREEN_PATH = "/ecoBike/views/fxml/transaction-error.fxml";
    public static final String RETURN_BIKE_SCREEN_PATH = "/ecoBike/views/fxml/return-payment.fxml";
    public static final String BIKE_INFO_PATH = "/ecoBike/views/fxml/bike-information.fxml";
    public static final String RENT_BIKE_INFO_PATH = "/ecoBike/views/fxml/renting-bike.fxml";

    public static final Map<String, String> errorCodes = new HashMap<String, String>() {{
        put("00", "Transaction successfully");
        put("01", "Invalid card");
        put("02", "Insufficient account balance");
        put("03", "Internal Server Error");
        put("04", "Fraud transaction");
        put("05", "Missing transaction information");
        put("06", "Missing version information");
        put("07", "Invalid amount");
    }};
}