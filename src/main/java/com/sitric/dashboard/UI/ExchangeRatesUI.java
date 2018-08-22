package com.sitric.dashboard.UI;

/**
 * ExchangeRates UI class
 */

import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI
public class ExchangeRatesUI extends VerticalLayout {

    @Autowired
    private DashboardService service;

    Component addExchangeRatesWidget() {
        VerticalLayout exchangeRatesLayout = new VerticalLayout();

        // widget title
        Label widgetLabel = new Label("Курс валют");
        exchangeRatesLayout.addComponent(widgetLabel);

        // information about exchange rate
        Label USD = new Label();
        exchangeRatesLayout.addComponent(USD);

        Label EUR = new Label();
        EUR.setHeight("76");
        exchangeRatesLayout.addComponent(EUR);

        // update exchange rates info
        service.getExchangeRates(USD, EUR);

         // "обновить" button
        addUpdateWeatherButton(exchangeRatesLayout, USD, EUR);

        return exchangeRatesLayout;
    }

    private void addUpdateWeatherButton(VerticalLayout exchangeRatesLayout, Label USD, Label EUR) {
        exchangeRatesLayout.addComponent(
                new Button("обновить", clickEvent -> {
                    // update exchange rates info
                    service.getExchangeRates(USD, EUR);
                })
        );
    }
}