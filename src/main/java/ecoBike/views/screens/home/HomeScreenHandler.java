package ecoBike.views.screens.home;

import ecoBike.controller.HomeController;
import ecoBike.entity.dock.Dock;
import ecoBike.entity.rental.Rental;
import ecoBike.utils.Configs;
import ecoBike.views.screens.BaseScreenHandler;
import ecoBike.views.screens.dock.DockScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
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

public class HomeScreenHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private Button searchBtn;

    @FXML
    private HBox hboxHome;


    @FXML
    private ImageView homeLogo;

    @FXML
    private TextField searchInput;

    private List homeItems;
    private Rental rental;
    private String searchString = searchInput.getText();


    /**
     * constructor, when the user is not using any bike
     * @param stage
     * @param screenPath
     * @throws IOException
     * @throws SQLException
     */
    public HomeScreenHandler(Stage stage, String screenPath)  throws IOException, SQLException {
        super(stage, screenPath);
        this.rental = null;
        this.searchString = null;
        searchBtn.setOnMouseClicked(e -> {
            try {
                initHome(searchInput.getText(), this.rental);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.homeLogo.setOnMouseClicked(e -> {
            try {
                backToHome();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
        initHome(this.searchString, this.rental);
    }

    /**
     * constructor, when the user is using a rented bike
     * @param stage
     * @param screenPath
     * @param rental
     * @throws IOException
     * @throws SQLException
     */
    public HomeScreenHandler(Stage stage, String screenPath, Rental rental) throws IOException, SQLException {
        super(stage, screenPath);
        this.rental = rental;
        this.searchString = null;
        searchBtn.setOnMouseClicked(e -> {
            try {
                initHome(searchInput.getText(), this.rental);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        initHome(this.searchString, rental);
    }

    /**
     * Display the home screen
     * @param searchString
     * @param rental
     * @throws SQLException
     * @throws IOException
     */

    public void initHome(String searchString, Rental rental) throws SQLException, IOException {
        setBController(new HomeController());
        List medium = ((HomeController) getBController()).getAvailableDock();
        this.homeItems = new ArrayList<>();
        if (rental != null) {
            for (Object object : medium) {
                Dock dock = (Dock) object;
                DockHandler dockHandler = new DockHandler(Configs.DOCK_HOME_PATH, dock, this, rental);
                if (searchString == null) {
                    this.homeItems.add(dockHandler);
                } else if (dock.getName().toLowerCase().contains(searchString.toLowerCase()))
                    this.homeItems.add(dockHandler);
            }
        } else {
            for (Object object : medium) {
                Dock dock = (Dock) object;
                DockHandler dockHandler = new DockHandler(Configs.DOCK_HOME_PATH, dock, this);
                if (searchString == null) {
                    this.homeItems.add(dockHandler);
                } else if (dock.getName().toLowerCase().contains(searchString.toLowerCase()))
                    this.homeItems.add(dockHandler);
            }
        }
//        homeLogo.setOnMouseClicked(e -> {
//            System.out.println(this.homeItems);
//            addDockHome(this.homeItems);
//        });
        addDockHome(this.homeItems);
    }


    /**
     * add list dock to home screen
     * @param items
     */
    public void addDockHome(List items) {
        hboxHome.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });
        int rootSize = homeItems.size();
        if (!homeItems.isEmpty()) {
            int size = homeItems.size();
            hboxHome.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.setSpacing(20);
                while (vBox.getChildren().size() < Math.ceil((double)size/2) && !homeItems.isEmpty()) {
                    DockHandler dock = (DockHandler) homeItems.get(0);
                    vBox.getChildren().add(dock.getContent());
                    homeItems.remove(dock);
                }
            });
        }
        if(rootSize %2 != 0){
            VBox vBox = (VBox) hboxHome.getChildren().get(1);
            vBox.setSpacing(20);
            GridPane emptyPane = new GridPane();
            emptyPane.setPrefHeight(200.0);
            emptyPane.setPrefWidth(475.0);
            vBox.getChildren().add(emptyPane);
        }


    }

    /**
     * request to move to this home screen
     * @param prevScreen
     * @throws SQLException
     */
    public void requestToReturnHome(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        show();
    }

    public Stage getStage() {
        return this.stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
