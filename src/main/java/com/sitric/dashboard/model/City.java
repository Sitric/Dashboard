package com.sitric.dashboard.model;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class City {
    private String name;
    private Double latitude;    // широта
    private Double longitude;   // долгота

    public City() {
    }

    public City(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
