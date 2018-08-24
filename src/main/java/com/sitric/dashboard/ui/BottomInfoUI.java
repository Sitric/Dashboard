package com.sitric.dashboard.ui;


/**
 * BottomInfo ui class
 */

import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringUI
public class BottomInfoUI extends HorizontalLayout {

    @Autowired
    DashboardService service;

    @Autowired
    private HttpServletRequest request;

    private static Label timeLabel = new Label();

    static void setTitleForTimeLabel() {
        timeLabel.setValue("Информация по состоянию на " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()));
    }

    private HorizontalLayout bottomLayout;


    public BottomInfoUI() {
        this.bottomLayout = new HorizontalLayout();
    }

    Component addBottomInfo() {

        bottomLayout.setSizeFull();

        timeLabel.setWidth("70%");
        setTitleForTimeLabel();
        bottomLayout.addComponent(timeLabel);

        Label ipLabel = new Label("Ваш IP: " + service.getIP(request));
        ipLabel.setWidth("30%");
        bottomLayout.addComponent(ipLabel);

        bottomLayout.setComponentAlignment(timeLabel, Alignment.TOP_LEFT);
        bottomLayout.setComponentAlignment(ipLabel, Alignment.TOP_RIGHT);


        return bottomLayout;
    }
}
