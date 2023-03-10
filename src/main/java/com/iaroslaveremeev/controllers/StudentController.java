package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.App;
import com.iaroslaveremeev.model.Auto;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.repository.AutoRepository;
import com.iaroslaveremeev.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    public ListView<String> listViewCars;
    private Student value;
    private List<Auto> studentCars;

    @Override
    public void initData(Student value) {
        this.value = value;
        textFieldName.setText(value.getFio());
        textFieldAge.setText(Integer.toString(value.getAge()));
        textFieldNumber.setText(Integer.toString(value.getNum()));
        textFieldSalary.setText(Double.toString(value.getSalary()));
        AutoRepository autoRepository = new AutoRepository();
        this.studentCars = autoRepository.getStudentCars(value.getId());
        List<String> stringsCars = new ArrayList<>();
        for (int i = 0; i < studentCars.size(); i++) {
            stringsCars.add("id " + studentCars.get(i).getId() + ". " + studentCars.get(i).getBrand() + ", " +
                    (studentCars.get(i).getPower() + " hp, " +
                    studentCars.get(i).getYear()));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(stringsCars);
        listViewCars.setItems(observableList);
    }

    public void updateStudentData(ActionEvent actionEvent) {
        try {
            this.value.setFio(textFieldName.getText());
            this.value.setAge(Integer.parseInt(textFieldAge.getText()));
            this.value.setNum(Integer.parseInt(textFieldNumber.getText()));
            this.value.setSalary(Double.parseDouble(textFieldSalary.getText()));
            StudentRepository studentRepository = new StudentRepository();
            studentRepository.update(this.value);
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update failed. " +
                    "Check input spelling, connection and server settings!");
            alert.show();
        }
    }

    public void deleteStudent(ActionEvent actionEvent) {
        try {
            int id = this.value.getId();
            StudentRepository studentRepository = new StudentRepository();
            studentRepository.delete(id);
            Stage stage = (Stage) deleteStudent.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Deletion failed. " +
                    "Check connection and server settings!");
            alert.show();
        }
    }
    public Auto autoFromString(String autoString) throws IOException {
        try {
            AutoRepository autoRepository = new AutoRepository();
            Auto car = new Auto();
            String[] strings = autoString.replace(".", "").substring(3).split(" ");
            int carId = Integer.parseInt(strings[0]);
            car = autoRepository.get(carId);
            return car;
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cars' data initialization failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }
    public void listViewClick(MouseEvent mouseEvent) {
        try {
            if(mouseEvent.getClickCount() == 2){
                App.openWindow("/car.fxml", autoFromString(this.listViewCars.getSelectionModel().getSelectedItem()));
                Stage stage = (Stage) deleteStudent.getScene().getWindow();
                stage.close();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cars' data initialization failed." +
                    " Check connection and server settings!");
            alert.show();
        }
    }
}
