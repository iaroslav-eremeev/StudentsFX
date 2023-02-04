package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewStudentController {
    public TextField textFieldName;
    public TextField textFieldAge;
    public TextField textFieldNumber;
    public TextField textFieldSalary;
    private Student value;

    public void addNewStudent(ActionEvent actionEvent) throws IOException {
        this.value = new Student();
        this.value.setFio(textFieldName.getText());
        this.value.setAge(Integer.parseInt(textFieldAge.getText()));
        this.value.setNum(Integer.parseInt(textFieldNumber.getText()));
        this.value.setSalary(Double.parseDouble(textFieldSalary.getText()));
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.add(this.value);
        Stage stage = (Stage) textFieldSalary.getScene().getWindow();
        stage.close();
    }

    public void clearAllFields(ActionEvent actionEvent) {
        textFieldAge.clear();
        textFieldName.clear();
        textFieldNumber.clear();
        textFieldSalary.clear();
    }
}
