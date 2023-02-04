package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentController implements ControllerData<Student> {

    public TextField textFieldName;
    public TextField textFieldAge;
    public TextField textFieldNumber;
    public TextField textFieldSalary;
    public Button deleteStudent;
    public ListView<String> ListViewCars;
    private Student value;

    @Override
    public void initData(Student value) {
        this.value = value;
        textFieldName.setText(value.getFio());
        textFieldAge.setText(Integer.toString(value.getAge()));
        textFieldNumber.setText(Integer.toString(value.getNum()));
        textFieldSalary.setText(Double.toString(value.getSalary()));
        AutoRepository autoRepository = new AutoRepository();
        List<Auto> studentCars = autoRepository.getStudentCars(value.getId());
        List<String> stringsCars = new ArrayList<>();
        for (int i = 0; i < studentCars.size(); i++) {
            stringsCars.add(i+1 + ". " + studentCars.get(i).getBrand() + ", " + (studentCars.get(i).getPower() + " hp, " +
                    studentCars.get(i).getYear()));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(stringsCars);
        ListViewCars.setItems(observableList);
    }

    public void updateStudentData(ActionEvent actionEvent) throws IOException {
        this.value.setFio(textFieldName.getText());
        this.value.setAge(Integer.parseInt(textFieldAge.getText()));
        this.value.setNum(Integer.parseInt(textFieldNumber.getText()));
        this.value.setSalary(Double.parseDouble(textFieldSalary.getText()));
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.update(this.value);
    }

    public void deleteStudent(ActionEvent actionEvent) throws IOException {
        int id = this.value.getId();
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.delete(id);
        Stage stage = (Stage) deleteStudent.getScene().getWindow();
        stage.close();
    }

}
