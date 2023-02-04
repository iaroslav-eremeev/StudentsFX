package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.App;
import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class StudentMainController {
    @FXML
    private TableView<Student> studentTable;

    public void initialize() throws IOException {
        initStudentTable();
    }

    public void buttonSaveFile(ActionEvent actionEvent) {
        App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
    }

    public void getStudentsButton(ActionEvent actionEvent) throws IOException {
        initStudentTable();
    }

    public void getCarsButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/carMain.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(fxmlLoader.load(), 500, 500));
        stage.show();
        Stage close = (Stage) this.studentTable.getScene().getWindow();
        close.close();
    }

    private void initStudentTable() throws IOException {
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
        id.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.1));
        id.setResizable(false);
        name.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.3));
        name.setResizable(false);
        age.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.2));
        age.setResizable(false);
        num.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.2));
        num.setResizable(false);
        salary.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.2));
        salary.setResizable(false);

        StudentRepository studentRepository = new StudentRepository();
        List<Student> students = studentRepository.get();
        ObservableList<Student> studentsList = FXCollections.observableArrayList(students);
        studentTable.setItems(studentsList);
        studentTable.getColumns().setAll(id, name, age, num, salary);
    }

    public void tableClick(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2){
            App.openWindow("/student.fxml", this.studentTable.getSelectionModel().getSelectedItem());
            initStudentTable();
        }
    }
}
