package com.sitric.dashboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayExchangeRates;
import com.sitric.dashboard.model.DisplayForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Value("${yandex.apikey}")
    private String apikey;

    @Override
    public DisplayForecast getForecast(Optional<City> city) throws MalformedURLException {
        StringBuilder url = new StringBuilder("https://api.weather.yandex.ru/v1/forecast");
        url.append("?lat=" + city.get().getLatitude());
        url.append("&lon=" + city.get().getLongitude());
        url.append("&lang=ru_RU");
        url.append("&limit=2");
        url.append("&hours=false");
        URL obj = new URL(url.toString());

        DisplayForecast df = new DisplayForecast();
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("X-Yandex-API-Key", apikey);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder result = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
            df = mapper.readValue(result.toString(), DisplayForecast.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return df;

    }

    @Override
    public DisplayExchangeRates getExchangeRates() {
        String url = "https://www.cbr-xml-daily.ru/daily_json.js";
        DisplayExchangeRates der = new DisplayExchangeRates();
        ObjectMapper mapper = new ObjectMapper();

        HttpURLConnection connection = null;
        try {
            URL obj = new URL(url);
            try {
                connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder result = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }
                in.close();
                der = mapper.readValue(result.toString(), DisplayExchangeRates.class);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return der;
    }

    @Override
    public String getIP() {
        String ip = null;
        URL whatismyip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                ip = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return ip;
    }

    @Override
    public List<City> generateCityList() {
        return Arrays.asList(
                new City("Москва", 55.73870, 37.61856),
                new City("Новосибирск", 54.8009038, 82.7511322),
                new City("Краснодар", 44.9679583, 38.8458879)
        );
    }
}
