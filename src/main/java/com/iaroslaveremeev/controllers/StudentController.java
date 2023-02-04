package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StudentController implements ControllerData<Student> {

    public TextField textFieldName;
    public TextField textFieldAge;
    public TextField textFieldNumber;
    public TextField textFieldSalary;
    private Student value;

    @Override
    public void initData(Student value) {
        this.value = value;
        textFieldName.setText(value.getFio());
        textFieldAge.setText(Integer.toString(value.getAge()));
        textFieldNumber.setText(Integer.toString(value.getNum()));
        textFieldSalary.setText(Double.toString(value.getSalary()));
    }

    public void updateStudentData(ActionEvent actionEvent) throws IOException {
        this.value.setFio(textFieldName.getText());
        this.value.setAge(Integer.parseInt(textFieldAge.getText()));
        this.value.setNum(Integer.parseInt(textFieldNumber.getText()));
        this.value.setSalary(Double.parseDouble(textFieldSalary.getText()));
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.update(this.value);
    }
}
