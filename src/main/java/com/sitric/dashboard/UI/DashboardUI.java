package com.sitric.dashboard.UI;

import com.sitric.dashboard.service.DashboardService;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "")
public class DashboardUI extends UI {

    @Autowired
    private DashboardService service;

    @Autowired
    private WeatherForecastUI weatherForecastUI;

    @Autowired
    private ExchangeRatesUI exchangeRatesUI;

    @Autowired
    private GuestCounterUI guestCounterUI;

    public DashboardUI(DashboardService service, WeatherForecastUI weatherForecastUI, ExchangeRatesUI exchangeRatesUI, GuestCounterUI guestCounterUI) {

        this.service = service;
        this.weatherForecastUI = weatherForecastUI;
        this.exchangeRatesUI = exchangeRatesUI;
        this.guestCounterUI = guestCounterUI;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout outerLayout = new VerticalLayout();
        HorizontalLayout widgetLayout = new HorizontalLayout();
        HorizontalLayout bottomLayout = new HorizontalLayout();
        widgetLayout.setSizeFull();


        Label headerLabel = new Label("Тестовое сетевое приложение");
        outerLayout.addComponent(headerLabel);
        outerLayout.setComponentAlignment(headerLabel, Alignment.TOP_CENTER);

        Component forecast = weatherForecastUI.addForecastWidget();
        widgetLayout.addComponent(forecast);
        widgetLayout.setComponentAlignment(forecast, Alignment.TOP_LEFT);

        Component rate = exchangeRatesUI.addExchangeRatesWidget();
        widgetLayout.addComponent(rate);
        widgetLayout.setComponentAlignment(rate, Alignment.TOP_CENTER);

        Component counter = guestCounterUI.addGuestCounterWidget();
        widgetLayout.addComponent(counter);
        widgetLayout.setComponentAlignment(counter, Alignment.TOP_RIGHT);

        Label timeLabel = new Label("Информация по состоянию на 16.07.2018 15:31:44");
        bottomLayout.addComponent(timeLabel);
        bottomLayout.setComponentAlignment(timeLabel, Alignment.TOP_LEFT);

        Label ipLabel = new Label("Ваш IP: " + service.getIP());
        bottomLayout.addComponent(ipLabel);
        bottomLayout.setComponentAlignment(ipLabel, Alignment.TOP_RIGHT);


        outerLayout.addComponent(widgetLayout);
        outerLayout.addComponent(bottomLayout);
        setContent(outerLayout);


    }
}
