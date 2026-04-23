module com.techprep {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires reactfx;

    opens com.controllers to javafx.fxml;

    opens com.techprep to javafx.fxml;
    exports com.techprep;

    opens com.model to javafx.fxml;
    exports com.model;
}
