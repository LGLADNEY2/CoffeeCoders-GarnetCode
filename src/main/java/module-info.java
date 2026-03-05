module com.techprep {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.techprep to javafx.fxml;
    exports com.techprep;
    exports com.model;
}
