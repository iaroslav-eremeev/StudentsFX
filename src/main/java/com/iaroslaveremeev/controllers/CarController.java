package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarController implements ControllerData<Auto> {
    public TextField textFieldBrand;
    public TextField textFieldPower;
    public TextField textFieldYear;
    public TextField textFieldIdStudent;
    public Label studentInfo;
    public Button deleteCar;
    private Auto value;

    @Override
    public void initData(Auto value) throws IOException {
        this.value = value;
        textFieldBrand.setText(value.getBrand());
        textFieldPower.setText(Integer.toString(value.getPower()));
        textFieldYear.setText(Integer.toString(value.getYear()));
        textFieldIdStudent.setText(Integer.toString(value.getIdStudent()));
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

    public void updateCarData(ActionEvent actionEvent) {
    }

    public void deleteCar(ActionEvent actionEvent) {
    }
}
