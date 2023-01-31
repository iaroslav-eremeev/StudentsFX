package com.iaroslaveremeev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.model.Auto;
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

public class AutoRepository {
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

    public List<Auto> get() throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto",
                "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, new TypeReference<>() {});
        }
    }

    public Auto get(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto?id=" + id, "GET")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, Auto.class);
        }
    }

    public Auto add(Auto auto) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/auto?" +
                "&brand=" + URLEncoder.encode(auto.getBrand(), StandardCharsets.UTF_8) +
                "&power=" + auto.getPower() +
                "&year=" + auto.getYear() +
                "&id_s=" + auto.getIdStudent(), "POST");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Auto.class);
    }

    public Auto update(Auto auto) throws IOException {
        InputStream inputStream = getData(Constants.SERVER_URL + "/auto?" +
                "id=" + auto.getId() +
                "&brand=" + URLEncoder.encode(auto.getBrand(), StandardCharsets.UTF_8) +
                "&power=" + auto.getPower() +
                "&year=" + auto.getYear() +
                "&id_s=" + auto.getIdStudent(), "PUT");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Auto.class);
    }

    public Auto delete(int id) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/auto?id=" + id,
                "DELETE")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, Auto.class);
        }
    }
}
