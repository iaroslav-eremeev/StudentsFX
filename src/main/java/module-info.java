module com.iaroslaveremeev {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.iaroslaveremeev to javafx.fxml;
    exports com.iaroslaveremeev;
}
