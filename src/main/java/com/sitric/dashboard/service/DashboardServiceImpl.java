package com.sitric.dashboard.service;

/**
 * Implementation of DashboardService
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayExchangeRates;
import com.sitric.dashboard.model.DisplayForecast;
import com.sitric.dashboard.model.GuestCounter;
import com.sitric.dashboard.repository.GuestCounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    GuestCounterRepository repository;

    @Value("${yandex.apikey}")
    private String apikey;

    /*
    * getForecast method
    *
    * input parameter instance of City class.
    * output parameter instance of DisplayForecast class
    * It makes a request to API Yandex.Weather, used the latitude and longitude of the city
    * and receives weather forecast in JSON.
    */

    @Override
    public DisplayForecast getForecast(Optional<City> city){
        DisplayForecast df = new DisplayForecast();
        if (city.isPresent()){
            logger.debug("Requesting weather forecast for " + city.get().getName());

            String url = "https://api.weather.yandex.ru/v1/forecast" +
                    "?lat=" + city.get().getLatitude() +
                    "&lon=" + city.get().getLongitude() +
                    "&lang=ru_RU" +
                    "&limit=2" +
                    "&hours=false";
            try {
                URL obj = new URL(url);
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

                    logger.debug("Requested weather forecast for " + city.get().getName() + " was successfully executed");
                } catch (IOException e) {
                    df.setWeatherToday(null);
                    df.setWeatherTomorrow(null);

                    logger.error("I/O Exception when trying to request for API Yandex.Weather. Check application.property: yandex.apikey");
                    //e.printStackTrace();
                }

            } catch (MalformedURLException e) {

                df.setWeatherToday(null);
                df.setWeatherTomorrow(null);
                logger.error("Incorrect format for URL when trying to request for API Yandex.Weather");
                //e.printStackTrace();
            }
        }
        else {
            logger.error("Error when passing object of class City to method getForecast");
        }
       return df;
    }

    /*
     * getExchangeRates method
     *
     * output parameter instance of DisplayForecast class
     * It makes a request to API Central Bank of Russia and receives exchange rates in JSON.
     */

    @Override
    public DisplayExchangeRates  getExchangeRates() {
        logger.debug("Requesting for exchange rates");

        DisplayExchangeRates der = new DisplayExchangeRates();

        String url = "https://www.cbr-xml-daily.ru/daily_json.js";

        ObjectMapper mapper = new ObjectMapper();

        HttpURLConnection connection;
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

                logger.debug("Requested exchange rates was successfully executed");
            } catch (IOException e) {
                der.setEURRate(null);
                der.setUSDRate(null);

                logger.error("I/O Exception when trying to request for API Central Bank of Russia");
            }

        } catch (MalformedURLException e) {

            der.setEURRate(null);
            der.setUSDRate(null);
            logger.error("Incorrect format for URL when trying to request for API Central Bank of Russia");
            //e.printStackTrace();
        }
        return der;
    }

    /*
     * getGuestCounter method
     *
     * output parameter is count of visits to the site
     * used MongoDB for data persistence
     *
     */

    @Override
    public Long getGuestCounter() {
        logger.debug("Determine the number of visits to the site");
        Long counter = null;
        if (repository.count() == 0) {
            setFirstValue();
            counter = 1L;
        }
        else {
            Optional<GuestCounter> optionalGc = repository.findById(1L);
            if (optionalGc.isPresent()) {
                GuestCounter gc = optionalGc.get();
                repository.save(new GuestCounter(gc.getId(), gc.getCounter() + 1, new Date()));
                counter = gc.getCounter() + 1;
                logger.debug("Number of visits to the site successfully determined");
            }
            else {
                logger.error("An error occurred while extracting GuestCounter data from MongoDB");
            }
        }
        return counter;
    }


    /*
     * getActualDateTime method
     *
     * output parameter is current date and time in dd.MM.yyyy HH:mm:ss format
     * used MongoDB for data persistence
     *
     */


    @Override
    public String getActualDateTime() {
        logger.debug("Trying to get actual date and time");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date actualDate = null;
        if (repository.count() == 0) {
            actualDate = setFirstValue();
        }
        else {
            Optional<GuestCounter> optionalGc = repository.findById(1L);
            if (optionalGc.isPresent()) {
                actualDate = optionalGc.get().getCurrentDate();

            }
            else {
                logger.error("An error occurred while extracting actual date and time from MongoDB");
            }
        }

        return sdf.format(actualDate);
    }

    private Date setFirstValue(){
        GuestCounter gc = new GuestCounter(1L, 1L, new Date());
        repository.save(new GuestCounter(1L, 1L, new Date()));
        return gc.getCurrentDate();
    }

    @Override
    public String getIP() {
        logger.debug("Requesting for remote IP address");
        String ip = null;
        URL whatIsMyIP;
        try {
            whatIsMyIP = new URL("http://checkip.amazonaws.com");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(whatIsMyIP.openStream()));
                ip = in.readLine();
                logger.debug("Remote IP address successfully determined");
            } catch (IOException e) {
                ip = "не доступно";
                logger.error("I/O Exception when trying to request for http://checkip.amazonaws.com");
                //e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            ip = "не доступно";
            logger.error("incorrect format for URL when trying to request for API Central Bank of Russia");
            //e.printStackTrace();
        }
        return ip;
    }


    /*
     * generateCityList method
     *
     * generate list of Cities for testing application
     *
     */

    @Override
    public List<City> generateCityList() {
        return Arrays.asList(
                new City("Москва", 55.73870, 37.61856),
                new City("Новосибирск", 54.8009038, 82.7511322),
                new City("Краснодар", 44.9679583, 38.8458879),
                new City("Уфа", 54.738762, 55.972055),
                new City("Новый Уренгой", 66.085884, 76.685746),
                new City("Каир (Египет)", 30.043084, 31.235274),
                new City("Оттава (Канада)", 45.387847, -75.666958)

        );
    }
}
