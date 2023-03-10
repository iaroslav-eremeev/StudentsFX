package com.iaroslaveremeev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.Auto;
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

public class AutoRepository {

    public AutoRepository() {
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cars' data initialization failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public List<Auto> get() throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto",
                "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<List<Auto>> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Getting the list of cars failed." +
                    " Check connection and server settings!");
            alert.show();
        }
        return null;
    }

    public List<Auto> getStudentCars(int idStudent){
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto?id_s=" + idStudent,
                "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<List<Auto>> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Getting student by id failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Auto get(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto?id=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Auto> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Getting cars' data failed." +
                    " Check connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Auto add(Auto auto) throws IOException {
        try {
            InputStream inputStream = getData(Constants.SERVER_URL + "/auto?" +
                    "&brand=" + URLEncoder.encode(auto.getBrand(), StandardCharsets.UTF_8) +
                    "&power=" + auto.getPower() +
                    "&year=" + auto.getYear() +
                    "&id_s=" + auto.getIdStudent(), "POST");
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Auto> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Adding a new car failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Auto update(Auto auto) throws IOException {
        try {
            InputStream inputStream = getData(Constants.SERVER_URL + "/auto?" +
                    "id=" + auto.getId() +
                    "&brand=" + URLEncoder.encode(auto.getBrand(), StandardCharsets.UTF_8) +
                    "&power=" + auto.getPower() +
                    "&year=" + auto.getYear() +
                    "&id_s=" + auto.getIdStudent(), "PUT");
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Auto> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update of car data failed." +
                    " Check input spelling, connection and server settings!");
            alert.show();
        }
        return null;
    }

    public Auto delete(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto?id=" + id,
                "DELETE")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<Auto> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Car deletion failed." +
                    " Check connection and server settings!");
            alert.show();
        }
        return null;
    }
}
