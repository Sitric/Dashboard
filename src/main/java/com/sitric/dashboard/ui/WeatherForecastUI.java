package com.sitric.dashboard.ui;

/**
 * WeatherForecast ui class
 */

import com.sitric.dashboard.helper.Broadcaster;
import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayForecast;
import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WeatherForecastUI extends VerticalLayout{

    @Autowired
    private DashboardService service;

    private VerticalLayout forecastLayout;

    public WeatherForecastUI() {



        this.forecastLayout = new VerticalLayout();
        this.forecastLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        forecastLayout.setWidth("33%");
        forecastLayout.setHeight("300");
        forecastLayout.setStyleName(ValoTheme.LAYOUT_WELL);
    }

    Component addForecastWidget() {

        // widget title
        Label widgetLabel = new Label("Прогноз погоды");
        widgetLabel.addStyleName(ValoTheme.LABEL_H3);
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
        DisplayForecast df = service.getForecast(comboBox.getSelectedItem());

        //set titles for weather
        setTitleForWeatherLabels(df, weatherCurrent, forecastLabel);


        // button "обновить"
        addUpdateWeatherButton(comboBox, weatherCurrent, forecastLabel);


        return forecastLayout;
    }

    void addUpdateWeatherButton(ComboBox<City> comboBox, Label weatherCurrent, Label forecastLabel) {
        Button updButton = new Button("обновить");
        updButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        updButton.addClickListener(clickEvent -> {


            //update weather data from API Yandex.Weather
            DisplayForecast df = service.getForecast(comboBox.getSelectedItem());
            setTitleForWeatherLabels(df, weatherCurrent, forecastLabel);

            Broadcaster.broadcast("Информация по состоянию на " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()));
        });

        forecastLayout.addComponent(updButton);
    }

    private void setTitleForWeatherLabels(DisplayForecast df, Label weatherCurrent, Label forecastLabel){
        if (df.getWeatherToday() == null){
            weatherCurrent.setValue("Отсутствует соединение с Яндекс.Погода");
            forecastLabel.setValue("");
        }
        else {
            weatherCurrent.setValue("Температура текущая " + df.getWeatherToday() + "° C");
            forecastLabel.setValue("Прогноз на завтра " + df.getWeatherTomorrow() + "° C");
        }
    }
}
