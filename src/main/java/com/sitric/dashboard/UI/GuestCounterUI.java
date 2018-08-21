package com.sitric.dashboard.UI;

import com.sitric.dashboard.service.DashboardService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class GuestCounterUI extends VerticalLayout {

    @Autowired
    private DashboardService service;
    private VerticalLayout counterLayout;

    Component addGuestCounterWidget() {
        counterLayout = new VerticalLayout();

        // название виджета
        Label widgetLabel = new Label("Счетчик посещений");
        counterLayout.addComponent(widgetLabel);

        Label guestCounterLabel = new Label("15");
        guestCounterLabel.setHeight("120");
        guestCounterLabel.setSizeFull();
        counterLayout.addComponent(guestCounterLabel);
        counterLayout.setComponentAlignment(guestCounterLabel, Alignment.BOTTOM_CENTER);

        return counterLayout;
    }
}
