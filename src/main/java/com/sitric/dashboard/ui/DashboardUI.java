package com.sitric.dashboard.ui;

/**
 * Main ui class
 */


import com.sitric.dashboard.helper.Broadcaster;
import com.sitric.dashboard.service.DashboardService;
import com.vaadin.annotations.Push;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Push
@SpringUI(path = "")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DashboardUI extends UI implements Broadcaster.BroadcastListener{

    private static Logger logger = LoggerFactory.getLogger(DashboardUI.class);

    @Autowired
    DashboardService service;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WeatherForecastUI weatherForecastUI;


    @Autowired
    private ExchangeRatesUI exchangeRatesUI;

    @Autowired
    private GuestCounterUI guestCounterUI;


    //main layout
    private VerticalLayout root;

    private Label timeLabel = new Label();

    public DashboardUI(WeatherForecastUI weatherForecastUI, ExchangeRatesUI exchangeRatesUI, GuestCounterUI guestCounterUI) {

        this.weatherForecastUI = weatherForecastUI;
        this.exchangeRatesUI = exchangeRatesUI;
        this.guestCounterUI = guestCounterUI;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {

            Page.getCurrent().setTitle("Dashboard application");
            setupLayout();
            addHeader();
            addWidgetPanel();
            addBottomInfo();
            Broadcaster.register(this);

        } catch (DataAccessResourceFailureException e) {

            logger.error("An error occurred while trying to connect to the Database");

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


    //block contains info about IP and actual date and time
    private void addBottomInfo() {
        HorizontalLayout bottomLayout = new HorizontalLayout();

        bottomLayout.setSizeFull();

        timeLabel.setWidth("70%");
        setTitleForTimeLabel();
        bottomLayout.addComponent(timeLabel);

        Label ipLabel = new Label("Ваш IP: " + service.getIP(request));
        ipLabel.setWidth("30%");
        bottomLayout.addComponent(ipLabel);

        bottomLayout.setComponentAlignment(timeLabel, Alignment.TOP_LEFT);
        bottomLayout.setComponentAlignment(ipLabel, Alignment.TOP_RIGHT);

        root.addComponent(bottomLayout);
    }

    // update actual date and time
    private void setTitleForTimeLabel() {
        timeLabel.setValue("Информация по состоянию на " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()));
    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receiveBroadcast(final String message) {
        // Must lock the session to execute logic safely
        access(() -> {
            // Show it somehow
           timeLabel.setValue(message);
        });
    }

}
