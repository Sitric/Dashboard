package com.sitric.dashboard.UI;

import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayForecast;
import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.util.List;

@SpringUI(path = "")
public class WeatherForecastUI extends VerticalLayout {

    @Autowired
    private DashboardService service;

    private DisplayForecast displayForecast;
    private VerticalLayout forecastLayout;

     Component addForecastWidget() {
        forecastLayout = new VerticalLayout();

        // название виджета
        Label widgetLabel = new Label("Прогноз погоды");
        forecastLayout.addComponent(widgetLabel);

        //получаем список городов
        List<City> cities = service.generateCityList();

        //передаем их в ComboBox
        ComboBox<City> comboBox = new ComboBox<>();
        comboBox.setItems(cities);
        comboBox.setItemCaptionGenerator(City::getName);
        comboBox.setSelectedItem(cities.get(0));
        forecastLayout.addComponent(comboBox);

        try {
            displayForecast = service.getForecast(comboBox.getSelectedItem());

            // информация о погоде
            //TODO: вынести в отдельный метод
            Label weatherCurrent = new Label("Температура текущая " + displayForecast.getWeatherToday() + "° C");
            forecastLayout.addComponent(weatherCurrent);

            Label forecastLabel = new Label("Прогноз на завтра " + displayForecast.getWeatherTomorrow() + "° C");
            forecastLayout.addComponent(forecastLabel);

            //кнопка "обновить"
            addUpdateWeatherButton(comboBox, forecastLayout, weatherCurrent, forecastLabel);

        } catch (MalformedURLException e) {
            //TODO посмотреть картинку при обрыве соединения
            e.printStackTrace();
        }

        return forecastLayout;
    }

    void addUpdateWeatherButton(ComboBox<City> comboBox, Layout forecastLayout, Label weatherCurrent, Label forecastLabel) {
        forecastLayout.addComponent(
                new Button("обновить", clickEvent -> {

                    try {
                        displayForecast = service.getForecast(comboBox.getSelectedItem());
                        weatherCurrent.setValue("Температура текущая " + displayForecast.getWeatherToday() + "° C");
                        forecastLabel.setValue("Прогноз на завтра " + displayForecast.getWeatherTomorrow() + "° C");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                })
        );
    }

}
