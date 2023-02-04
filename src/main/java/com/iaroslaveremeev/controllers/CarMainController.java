package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.App;
import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class CarMainController {
    public TableView carTable;

    public void buttonSaveFile(ActionEvent actionEvent) {
        App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
    }

    public void getStudentsButton(ActionEvent actionEvent) {

    }

    public void getCarsButton(ActionEvent actionEvent) throws IOException {
        initCarsTable();
    }

    private void initCarsTable() throws IOException {
        TableColumn<Auto, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Auto, String> brand = new TableColumn<>("BRAND");
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<Auto, Integer> power = new TableColumn<>("POWER");
        power.setCellValueFactory(new PropertyValueFactory<>("power"));
        TableColumn<Auto, Integer> year = new TableColumn<>("YEAR");
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        TableColumn<Auto, Integer> idStudent = new TableColumn<>("STUDENT ID");
        idStudent.setCellValueFactory(new PropertyValueFactory<>("id_s"));
        id.prefWidthProperty().bind(carTable.widthProperty().multiply(0.1));
        id.setResizable(false);
        brand.prefWidthProperty().bind(carTable.widthProperty().multiply(0.3));
        brand.setResizable(false);
        power.prefWidthProperty().bind(carTable.widthProperty().multiply(0.2));
        power.setResizable(false);
        year.prefWidthProperty().bind(carTable.widthProperty().multiply(0.2));
        year.setResizable(false);
        idStudent.prefWidthProperty().bind(carTable.widthProperty().multiply(0.2));
        idStudent.setResizable(false);

        AutoRepository autoRepository = new AutoRepository();
        List<Auto> cars = autoRepository.get();
        ObservableList<Auto> carsList = FXCollections.observableArrayList(cars);
        carTable.setItems(carsList);
        carTable.getColumns().setAll(id, brand, power, year, idStudent);
    }

    public void tableClick(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2){
            App.openWindow("/student.fxml", this.carTable.getSelectionModel().getSelectedItem());
            initCarsTable();
        }
    }
}
