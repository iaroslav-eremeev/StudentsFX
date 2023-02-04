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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    public TableView table;

    private boolean tableShowsStudents;

    public void initialize() throws IOException {
    }

    public void buttonSaveFile(ActionEvent actionEvent) {
        App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
    }

    public void getStudentsButton(ActionEvent actionEvent) throws IOException {
        initStudentTable(this.table);
    }

    public void getCarsButton(ActionEvent actionEvent) throws IOException {
        initCarsTable(this.table);
    }

    private void initStudentTable(TableView<Student> tableView) throws IOException {
        tableView.getColumns().clear();
        this.tableShowsStudents = true;
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
        id.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        id.setResizable(false);
        name.prefWidthProperty().bind(tableView.widthProperty().multiply(0.29));
        name.setResizable(false);
        age.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        age.setResizable(false);
        num.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        num.setResizable(false);
        salary.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        salary.setResizable(false);

        StudentRepository studentRepository = new StudentRepository();
        List<Student> students = studentRepository.get();
        ObservableList<Student> studentsList = FXCollections.observableArrayList(students);
        tableView.setItems(studentsList);
        tableView.getColumns().setAll(id, name, age, num, salary);
    }

    private void initCarsTable(TableView tableView) throws IOException {
        tableView.getColumns().clear();
        this.tableShowsStudents = false;
        TableColumn<Auto, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Auto, String> brand = new TableColumn<>("BRAND");
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<Auto, Integer> power = new TableColumn<>("POWER");
        power.setCellValueFactory(new PropertyValueFactory<>("power"));
        TableColumn<Auto, Integer> year = new TableColumn<>("YEAR");
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        TableColumn<Auto, Integer> idStudent = new TableColumn<>("STUDENT ID");
        idStudent.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        id.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        id.setResizable(false);
        brand.prefWidthProperty().bind(tableView.widthProperty().multiply(0.29));
        brand.setResizable(false);
        power.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        power.setResizable(false);
        year.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        year.setResizable(false);
        idStudent.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        idStudent.setResizable(false);

        AutoRepository autoRepository = new AutoRepository();
        List<Auto> cars = autoRepository.get();
        ObservableList<Auto> carsList = FXCollections.observableArrayList(cars);
        tableView.setItems(carsList);
        tableView.getColumns().setAll(id, brand, power, year, idStudent);
    }

    public void tableClick(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2){
            if (tableShowsStudents){
                App.openWindow("/student.fxml", this.table.getSelectionModel().getSelectedItem());
                initStudentTable(this.table);
            }
        }
    }

    public void addStudent(ActionEvent actionEvent) throws IOException {
        App.openWindow("/newStudent.fxml", null);
        initStudentTable(this.table);
    }

    public void addCar(ActionEvent actionEvent) {
    }
}
