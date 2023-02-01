package com.iaroslaveremeev;

import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    public TableView<Student> mainTable;
    @FXML
    public TableColumn<Student, Integer> id;
    @FXML
    public TableColumn<Student, String> name;
    @FXML
    public TableColumn<Student, Integer> age;
    @FXML
    public TableColumn<Student, Integer> num;
    @FXML
    public TableColumn<Student, Double> salary;


    public void initialize() throws IOException {
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        age.setCellValueFactory(new PropertyValueFactory<>("AGE"));
        num.setCellValueFactory(new PropertyValueFactory<>("NUMBER"));
        salary.setCellValueFactory(new PropertyValueFactory<>("SALARY"));
    }

    public void buttonSaveFile(ActionEvent actionEvent) {
        App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
    }

    public void getStudentsButton(ActionEvent actionEvent) throws IOException {
        StudentRepository studentRepository = new StudentRepository();
        List<Student> students = studentRepository.get();
        ObservableList<Student> studentsList = FXCollections.observableArrayList();
        studentsList.addAll(students);
        mainTable.setItems(studentsList);
    }
}
