package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.App;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    public TableView<Student> mainTable;
    public void initialize() throws IOException {
    }

    public void buttonSaveFile(ActionEvent actionEvent) {
        App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
    }

    public void getStudentsButton(ActionEvent actionEvent) throws IOException {
        initTable();
    }

    private void initTable() throws IOException {
        TableColumn<Student, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> name = new TableColumn<>("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("fio"));
        TableColumn<Student, Integer> age = new TableColumn<>("AGE");
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn<Student, Integer> num = new TableColumn<>("NUMBER");
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        TableColumn<Student, Double> salary = new TableColumn<>("SALARY");
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        StudentRepository studentRepository = new StudentRepository();
        List<Student> students = studentRepository.get();
        ObservableList<Student> studentsList = FXCollections.observableArrayList(students);
        mainTable.setItems(studentsList);
        mainTable.getColumns().setAll(id, name, age, num, salary);
    }

    public void tableClick(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2){
            App.openWindow("/student.fxml", this.mainTable.getSelectionModel().getSelectedItem());
            initTable();
        }
    }
}
