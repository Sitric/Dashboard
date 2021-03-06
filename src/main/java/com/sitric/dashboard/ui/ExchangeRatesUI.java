package com.sitric.dashboard.ui;

/**
 * ExchangeRates ui class
 */

import com.sitric.dashboard.helper.Broadcaster;
import com.sitric.dashboard.model.DisplayExchangeRates;
import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExchangeRatesUI extends VerticalLayout{

    @Autowired
    private DashboardService service;

    private VerticalLayout exchangeRatesLayout;



    public ExchangeRatesUI() {
        this.exchangeRatesLayout = new VerticalLayout();
        this.exchangeRatesLayout.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);

        exchangeRatesLayout.setWidth("33%");
        exchangeRatesLayout.setHeight("300");
        exchangeRatesLayout.setStyleName(ValoTheme.LAYOUT_WELL);
    }

    Component addExchangeRatesWidget() {

        // widget title
        Label widgetLabel = new Label("Курс валют");
        exchangeRatesLayout.addComponent(widgetLabel);

        widgetLabel.addStyleName(ValoTheme.LABEL_H3);

        // information about exchange rate
        Label USD = new Label();
        exchangeRatesLayout.addComponent(USD);

        Label EUR = new Label();
        EUR.setHeight("90");
        exchangeRatesLayout.addComponent(EUR);

        // set exchange rates info from Central Bank of Russia
        DisplayExchangeRates der = service.getExchangeRates();
        setTitleForExchangeRatesLabels(der, USD, EUR);

        // "обновить" button
        addUpdateExchangeRatesButton(USD, EUR);

        return exchangeRatesLayout;
    }

    private void addUpdateExchangeRatesButton(Label USD, Label EUR) {
        Button updButton = new Button("обновить");
        updButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        updButton.addClickListener(clickEvent -> {

            // update exchange rates info
            setTitleForExchangeRatesLabels(service.getExchangeRates(), USD, EUR);

            //update actual date and time
            Broadcaster.broadcast("Информация по состоянию на " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()));
        });

        exchangeRatesLayout.addComponent(updButton);

    }

    private void setTitleForExchangeRatesLabels(DisplayExchangeRates der, Label USD, Label EUR){
        if (der.getEURRate() == null){
            EUR.setValue("Отсутствует соединение с ЦБ РФ");
            USD.setValue("");
        }
        else {
            USD.setValue("USD: " + der.getUSDRate() + " RUB");
            EUR.setValue("EUR: " + der.getEURRate() + " RUB");
        }
    }
}