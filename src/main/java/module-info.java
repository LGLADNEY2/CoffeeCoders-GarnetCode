module com.techprep {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.techprep to javafx.fxml;
    exports com.techprep;
}
