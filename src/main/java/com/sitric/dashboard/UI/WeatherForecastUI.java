package com.sitric.dashboard.UI;

/**
 * WeatherForecast UI class
 */

import com.sitric.dashboard.model.City;
import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI(path = "")
public class WeatherForecastUI extends VerticalLayout {

    @Autowired
    private DashboardService service;


    Component addForecastWidget() {
        VerticalLayout forecastLayout = new VerticalLayout();

        // widget title
        Label widgetLabel = new Label("Прогноз погоды");
        forecastLayout.addComponent(widgetLabel);

        //getting list of cities
        List<City> cities = service.generateCityList();

        //set names of cities into ComboBox
        ComboBox<City> comboBox = new ComboBox<>();
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setItems(cities);
        comboBox.setItemCaptionGenerator(City::getName);
        comboBox.setSelectedItem(cities.get(0));
        forecastLayout.addComponent(comboBox);


        // weather labels
        Label weatherCurrent = new Label();
        forecastLayout.addComponent(weatherCurrent);

        Label forecastLabel = new Label();
        forecastLayout.addComponent(forecastLabel);

        //get weather data from API Yandex.Weather
        service.getForecast(comboBox.getSelectedItem(), weatherCurrent, forecastLabel);


        // button "обновить"
        addUpdateWeatherButton(comboBox, forecastLayout, weatherCurrent, forecastLabel);


        return forecastLayout;
    }

    void addUpdateWeatherButton(ComboBox<City> comboBox, Layout forecastLayout, Label weatherCurrent, Label forecastLabel) {
        forecastLayout.addComponent(
            new Button("обновить", clickEvent -> {

                //update weather data from API Yandex.Weather
                service.getForecast(comboBox.getSelectedItem(), weatherCurrent, forecastLabel);
            })
        );
    }


}
