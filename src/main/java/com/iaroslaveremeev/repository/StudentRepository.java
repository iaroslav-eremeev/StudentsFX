package com.iaroslaveremeev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.model.Student;
import com.iaroslaveremeev.util.Constants;

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
    private static InputStream getData(String link, String method) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        if (httpURLConnection.getResponseCode() == 400) {
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getErrorStream()))) {
                String error = bufferedReader.readLine();
                throw new IllegalArgumentException(error);
            }
        }
        return httpURLConnection.getInputStream();
    }

    public List<Student> get() throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/students",
                "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, new TypeReference<>() {});
        }
    }

    public Student get(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/students?id=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, Student.class);
        }
    }

    public Student add(Student student) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/students?" +
                "&fio=" + URLEncoder.encode(student.getFio(), StandardCharsets.UTF_8) +
                "&age=" + student.getAge() +
                "&num=" + student.getNum() +
                "&salary=" + student.getSalary(), "POST");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Student.class);
    }

    public Student update(Student student) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/students?" +
                "id=" + student.getId() +
                "&fio=" + URLEncoder.encode(student.getFio(), StandardCharsets.UTF_8) +
                "&age=" + student.getAge() +
                "&num=" + student.getNum() +
                "&salary=" + student.getSalary(), "PUT");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Student.class);
    }

    public Student delete(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/students?id=" + id,
                "DELETE")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, Student.class);
        }
    }
}
