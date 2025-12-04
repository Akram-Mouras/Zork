module demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.magestale to javafx.fxml;
    exports com.magestale;
}
