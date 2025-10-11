module sys.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.compiler;
    requires java.desktop;
    requires java.sql;

    opens sys.main to javafx.fxml;
    exports sys.main;
    exports sys.controller;
    opens sys.controller to javafx.fxml;
}