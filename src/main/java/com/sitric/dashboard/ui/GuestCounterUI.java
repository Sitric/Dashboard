package com.sitric.dashboard.ui;

/**
 * GuestCounter ui class
 */

import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class GuestCounterUI extends VerticalLayout{

    @Autowired
    private DashboardService service;

    private VerticalLayout counterLayout;

    public GuestCounterUI() {
        this.counterLayout = new VerticalLayout();
        this.counterLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        counterLayout.setWidth("33%");
        counterLayout.setHeight("300");

        counterLayout.setStyleName(ValoTheme.LAYOUT_WELL);
    }

    Component addGuestCounterWidget() {

        // widget title
        Label widgetLabel = new Label("Счетчик посещений");

        widgetLabel.addStyleName(ValoTheme.LABEL_H3);
        widgetLabel.setHeight("50");


        counterLayout.addComponent(widgetLabel);

        Label guestCounterLabel = new Label(""+ service.getGuestCounter());
        guestCounterLabel.setHeight("250");

        counterLayout.addComponent(guestCounterLabel);
        counterLayout.setComponentAlignment(guestCounterLabel, Alignment.MIDDLE_CENTER);

        return counterLayout;
    }
}
