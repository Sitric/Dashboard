package com.sitric.dashboard.ui;

/**
 * Main ui class
 */

import com.sitric.dashboard.service.DashboardService;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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

    //main layout
    private VerticalLayout root;

    public DashboardUI(DashboardService service, WeatherForecastUI weatherForecastUI, ExchangeRatesUI exchangeRatesUI, GuestCounterUI guestCounterUI) {

        this.service = service;
        this.weatherForecastUI = weatherForecastUI;
        this.exchangeRatesUI = exchangeRatesUI;
        this.guestCounterUI = guestCounterUI;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Page.getCurrent().setTitle("Dashboard application");

        setupLayout();
        addHeader();
        addWidgetPanel();
        addBottomInfo();

    }



    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);
    }

    private void addHeader() {
        Label headerLabel = new Label("Тестовое сетевое приложение");
        headerLabel.addStyleName(ValoTheme.LABEL_H2);

        root.addComponent(headerLabel);
        root.setComponentAlignment(headerLabel, Alignment.TOP_CENTER);


    }

    private void addWidgetPanel() {
        HorizontalLayout widgetLayout = new HorizontalLayout();
        /*widgetLayout.setSizeFull();*/

//        Component forecast = weatherForecastUI.addForecastWidget();
        widgetLayout.addComponent(weatherForecastUI.addForecastWidget());
        //widgetLayout.setComponentAlignment(forecast, Alignment.TOP_LEFT);

//        Component rate = exchangeRatesUI.addExchangeRatesWidget();
        widgetLayout.addComponent(exchangeRatesUI.addExchangeRatesWidget());
//        widgetLayout.setComponentAlignment(rate, Alignment.TOP_CENTER);

//        Component counter = guestCounterUI.addGuestCounterWidget();
        widgetLayout.addComponent(guestCounterUI.addGuestCounterWidget());
//        widgetLayout.setComponentAlignment(counter, Alignment.TOP_RIGHT);

        root.addComponent(widgetLayout);

    }

    private void addBottomInfo() {
        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.setSizeFull();

        Label timeLabel = new Label("Информация по состоянию на " + service.getActualDateTime());
        timeLabel.setWidth("70%");
        bottomLayout.addComponent(timeLabel);


        Label ipLabel = new Label("Ваш IP: " + service.getIP());
        ipLabel.setWidth("30%");
        bottomLayout.addComponent(ipLabel);

        bottomLayout.setComponentAlignment(timeLabel, Alignment.TOP_LEFT);
        bottomLayout.setComponentAlignment(ipLabel, Alignment.TOP_RIGHT);

        root.addComponent(bottomLayout);
    }
}
