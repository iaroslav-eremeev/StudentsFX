module com.iaroslaveremeev {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.iaroslaveremeev to javafx.fxml;
    exports com.iaroslaveremeev;
    exports com.iaroslaveremeev.model;
    exports com.iaroslaveremeev.dto;
    exports com.iaroslaveremeev.controllers;
    opens com.iaroslaveremeev.controllers to javafx.fxml;
}
