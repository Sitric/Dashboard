package com.sitric.dashboard.ui;

/**
 * Main ui class
 */


import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;


@SpringUI(path = "")
public class DashboardUI extends UI {

    private static Logger logger = LoggerFactory.getLogger(DashboardUI.class);

    @Autowired
    private WeatherForecastUI weatherForecastUI;

    @Autowired
    private BottomInfoUI bottomInfoUI;

    @Autowired
    private ExchangeRatesUI exchangeRatesUI;

    @Autowired
    private GuestCounterUI guestCounterUI;


    //main layout
    private VerticalLayout root;

    public DashboardUI(WeatherForecastUI weatherForecastUI, ExchangeRatesUI exchangeRatesUI, GuestCounterUI guestCounterUI, BottomInfoUI bottomInfoUI) {

        this.weatherForecastUI = weatherForecastUI;
        this.exchangeRatesUI = exchangeRatesUI;
        this.guestCounterUI = guestCounterUI;
        this.bottomInfoUI =  bottomInfoUI;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {

            Page.getCurrent().setTitle("Dashboard application");
            setupLayout();
            addHeader();
            addWidgetPanel();
            addBottomInfo();

        } catch (DataAccessResourceFailureException e) {

            logger.error("an error occurred while trying to connect to the Database");

            Label exceptionLabel = new Label("Произошла ошибка при работе с базой данных");
            exceptionLabel.addStyleName(ValoTheme.LABEL_H2);
            root.addComponent(exceptionLabel);
        }


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

        widgetLayout.addComponent(weatherForecastUI.addForecastWidget());
        widgetLayout.addComponent(exchangeRatesUI.addExchangeRatesWidget());
        widgetLayout.addComponent(guestCounterUI.addGuestCounterWidget());

        root.addComponent(widgetLayout);

    }

    private void addBottomInfo() {
        root.addComponent(bottomInfoUI.addBottomInfo());
    }

}
