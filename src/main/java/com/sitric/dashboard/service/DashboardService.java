package com.sitric.dashboard.service;

import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayExchangeRates;
import com.sitric.dashboard.model.DisplayForecast;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface DashboardService {
    List<City> generateCityList();
    DisplayForecast getForecast(Optional<City> city) throws MalformedURLException;
    DisplayExchangeRates getExchangeRates();
    String getIP();
}
