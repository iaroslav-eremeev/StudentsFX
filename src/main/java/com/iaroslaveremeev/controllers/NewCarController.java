package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.repository.AutoRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewCarController {
    public TextField textFieldBrand;
    public TextField textFieldPower;
    public TextField textFieldYear;
    public TextField textFieldStudentId;
    private Auto value;

    public void addNewCar(ActionEvent actionEvent) throws IOException {
        this.value = new Auto();
        this.value.setBrand(textFieldBrand.getText());
        this.value.setPower(Integer.parseInt(textFieldPower.getText()));
        this.value.setYear(Integer.parseInt(textFieldYear.getText()));
        this.value.setIdStudent(Integer.parseInt(textFieldStudentId.getText()));
        AutoRepository autoRepository = new AutoRepository();
        autoRepository.add(this.value);
        Stage stage = (Stage) textFieldStudentId.getScene().getWindow();
        stage.close();
    }

    public void clearAllFields(ActionEvent actionEvent) {
        textFieldBrand.clear();
        textFieldPower.clear();
        textFieldYear.clear();
        textFieldStudentId.clear();
    }
}
