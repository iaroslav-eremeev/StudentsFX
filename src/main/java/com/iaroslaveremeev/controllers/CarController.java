package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.App;
import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.event.ActionEvent;
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
    public void initData(Auto value) throws IOException {
        this.value = value;
        StudentRepository studentRepository = new StudentRepository();
        this.student = studentRepository.get(this.value.getIdStudent());
        textFieldBrand.setText(value.getBrand());
        textFieldPower.setText(Integer.toString(value.getPower()));
        textFieldYear.setText(Integer.toString(value.getYear()));
        textFieldIdStudent.setText(Integer.toString(value.getIdStudent()));
        getStudentInfo();
    }

    private void getStudentInfo() throws IOException {
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
    }

    public void updateCarData(ActionEvent actionEvent) throws IOException {
        this.value.setBrand(textFieldBrand.getText());
        this.value.setPower(Integer.parseInt(textFieldPower.getText()));
        this.value.setYear(Integer.parseInt(textFieldYear.getText()));
        this.value.setIdStudent(Integer.parseInt(textFieldIdStudent.getText()));
        AutoRepository autoRepository = new AutoRepository();
        autoRepository.update(this.value);
        getStudentInfo();
    }

    public void deleteCar(ActionEvent actionEvent) throws IOException {
        int id = this.value.getId();
        AutoRepository autoRepository = new AutoRepository();
        autoRepository.delete(id);
        Stage stage = (Stage) deleteCar.getScene().getWindow();
        stage.close();
    }

    public void goToStudent(MouseEvent mouseEvent) throws IOException {
        App.openWindow("/student.fxml", this.student);
        Stage stage = (Stage) studentInfo.getScene().getWindow();
        stage.close();
    }
}
