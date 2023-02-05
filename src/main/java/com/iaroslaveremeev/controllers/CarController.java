package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.App;
import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CarController implements ControllerData<Auto> {
    public TextField textFieldBrand;
    public TextField textFieldPower;
    public TextField textFieldYear;
    public TextField textFieldIdStudent;
    public Label studentInfo;
    public Button deleteCar;
    private Auto value;
    private Student student;

    @Override
    public void initData(Auto value) {
        try {
            this.value = value;
            StudentRepository studentRepository = new StudentRepository();
            this.student = studentRepository.get(this.value.getIdStudent());
            textFieldBrand.setText(value.getBrand());
            textFieldPower.setText(Integer.toString(value.getPower()));
            textFieldYear.setText(Integer.toString(value.getYear()));
            textFieldIdStudent.setText(Integer.toString(value.getIdStudent()));
            getStudentInfo();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Students' data initialization failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
    }

    private void getStudentInfo() throws IOException {
        try {
            StudentRepository studentRepository = new StudentRepository();
            List<Student> students = studentRepository.get();
            String thisCarOwner = "";
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == this.value.getIdStudent()){
                    thisCarOwner = students.get(i).getFio() + ", " + students.get(i).getAge() + " years, №" +
                            students.get(i).getNum() + ", earns " + students.get(i).getSalary();
                }
            }
            studentInfo.setText(thisCarOwner);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Students' data initialization failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
    }

    public void updateCarData(ActionEvent actionEvent) throws IOException {
        try {
            this.value.setBrand(textFieldBrand.getText());
            this.value.setPower(Integer.parseInt(textFieldPower.getText()));
            this.value.setYear(Integer.parseInt(textFieldYear.getText()));
            this.value.setIdStudent(Integer.parseInt(textFieldIdStudent.getText()));
            AutoRepository autoRepository = new AutoRepository();
            autoRepository.update(this.value);
            getStudentInfo();
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update failed. " +
                    "Check input spelling, connection and server settings!");
            alert.show();
        }
    }

    public void deleteCar(ActionEvent actionEvent) {
        try {
            int id = this.value.getId();
            AutoRepository autoRepository = new AutoRepository();
            autoRepository.delete(id);
            Stage stage = (Stage) deleteCar.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Deletion failed. " +
                    "Check connection and server settings!");
            alert.show();
        }
    }

    public void goToStudent(MouseEvent mouseEvent) {
        try {
            App.openWindow("/student.fxml", this.student);
            Stage stage = (Stage) studentInfo.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Students' data initialization failed." +
                    " Check connection and server settings!");
            alert.show();
        }
    }
}
