package com.iaroslaveremeev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.util.Constants;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StudentRepository {

    public StudentRepository() {
    }

    private static InputStream getData(String link, String method) throws IOException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            if (httpURLConnection.getResponseCode() == 400) {
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getErrorStream()))) {
                    ResponseResult<Object> result = new ResponseResult<>(bufferedReader.readLine());
                    throw new IllegalArgumentException(result.getMessage());
                }
            }
            return httpURLConnection.getInputStream();
        } catch (IOException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Students' data initialization failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public List<Student> get() throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/students",
                "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<List<Student>> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Getting the list of students failed." +
                    " Check connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Student get(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/students?id=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Student> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Getting cars' data failed." +
                    " Check connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Student add(Student student) throws IOException {
        try {
            InputStream inputStream = getData(Constants.SERVER_URL + "/students?" +
                    "&fio=" + URLEncoder.encode(student.getFio(), StandardCharsets.UTF_8) +
                    "&age=" + student.getAge() +
                    "&num=" + student.getNum() +
                    "&salary=" + student.getSalary(), "POST");
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Student> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Adding a new student failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Student update(Student student) throws IOException {
        try {
            InputStream inputStream = getData(Constants.SERVER_URL + "/students?" +
                    "id=" + student.getId() +
                    "&fio=" + URLEncoder.encode(student.getFio(), StandardCharsets.UTF_8) +
                    "&age=" + student.getAge() +
                    "&num=" + student.getNum() +
                    "&salary=" + student.getSalary(), "PUT");
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Student> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update of student data failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Student delete(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/students?id=" + id,
                "DELETE")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Student> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Student deletion failed." +
                    " Check connection and server settings!");
            alert.show();
        }
        return null;
    }
}
