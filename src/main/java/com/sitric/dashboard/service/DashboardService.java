package com.sitric.dashboard.service;

/**
 * Service interface
 */

import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayExchangeRates;
import com.sitric.dashboard.model.DisplayForecast;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface DashboardService {
    List<City> generateCityList();
    DisplayForecast getForecast(Optional<City> city);
    DisplayExchangeRates getExchangeRates();
    String getIP(HttpServletRequest request);
    int getGuestCounter();
}
