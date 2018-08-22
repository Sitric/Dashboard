package com.sitric.dashboard.UI;

/**
 * GuestCounter UI class
 */

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

    Component addGuestCounterWidget() {
        VerticalLayout counterLayout = new VerticalLayout();

        // widget title
        Label widgetLabel = new Label("Счетчик посещений");
        counterLayout.addComponent(widgetLabel);

        Label guestCounterLabel = new Label(""+ service.getGuestCounter());
        guestCounterLabel.setHeight("120");
        guestCounterLabel.setSizeFull();
        counterLayout.addComponent(guestCounterLabel);
        counterLayout.setComponentAlignment(guestCounterLabel, Alignment.BOTTOM_CENTER);

        return counterLayout;
    }
}
