package ecoBike.views.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class FXMLScreenHandler {
    protected FXMLLoader loader;
    protected SplitPane content;

    public FXMLScreenHandler(String screenPath) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(screenPath));
        // Set this class as the controller
        this.loader.setController(this);
        this.content = (SplitPane)  loader.load();
    }

    public SplitPane getContent() {
        return this.content;
    }

    public FXMLLoader getLoader() {
        return this.loader;
    }

    public void setImage(ImageView imv, String path){
        Image img = new Image(getClass().getResourceAsStream(path));
        imv.setImage(img);
    }

    public static void main(){

    }
}
