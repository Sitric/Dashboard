package com.sitric.dashboard.UI;

import com.sitric.dashboard.model.City;
import com.sitric.dashboard.model.DisplayExchangeRates;
import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;

@SpringUI
public class ExchangeRatesUI extends VerticalLayout {

    @Autowired
    private DashboardService service;

    private DisplayExchangeRates displayExchangeRates;
    private VerticalLayout exchangeRatesLayout;

    Component addExchangeRatesWidget() {
        exchangeRatesLayout = new VerticalLayout();

        // название виджета
        Label widgetLabel = new Label("Курс валют");
        exchangeRatesLayout.addComponent(widgetLabel);

        displayExchangeRates = service.getExchangeRates();

        // информация о курсах валют
        Label USD = new Label("USD: " + displayExchangeRates.getUSDRate() + " RUB");
        exchangeRatesLayout.addComponent(USD);

        Label EUR = new Label("EUR: " + displayExchangeRates.getEURRate() + " RUB");
        EUR.setHeight("76");
        exchangeRatesLayout.addComponent(EUR);

        //кнопка "обновить"
        addUpdateWeatherButton(exchangeRatesLayout, USD, EUR);



        return exchangeRatesLayout;
    }

    void addUpdateWeatherButton(VerticalLayout exchangeRatesLayout, Label USD, Label EUR) {
        exchangeRatesLayout.addComponent(
                new Button("обновить", clickEvent -> {
                        displayExchangeRates = service.getExchangeRates();
                        USD.setValue("USD: " + displayExchangeRates.getUSDRate() + " RUB");
                        EUR.setValue("EUR: " + displayExchangeRates.getEURRate() + " RUB");
                })
        );
    }

}