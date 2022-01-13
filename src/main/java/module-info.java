module com.example.programming {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.net.http;
//
//    opens com.example.programming to javafx.fxml;
//    exports com.example.programming;

    opens ecoBike to javafx.fxml;
    exports ecoBike;

    opens ecoBike.views.screens to javafx.fxml;
    opens ecoBike.views.screens.home to javafx.fxml;
    opens ecoBike.views.screens.dock to javafx.fxml;
    opens ecoBike.views.screens.bike to javafx.fxml;
    opens ecoBike.views.screens.payment to javafx.fxml;
    opens ecoBike.views.screens.rental to javafx.fxml;

}