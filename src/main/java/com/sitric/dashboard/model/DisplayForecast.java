package com.sitric.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.spring.annotation.SpringComponent;

import java.util.Map;

@SpringComponent
@JsonIgnoreProperties(ignoreUnknown=true)
public class DisplayForecast {

    private Integer weatherToday;

    private Integer weatherTomorrow;

    @JsonProperty("now")
    private Long currentTime;

    public DisplayForecast() {
    }

    public DisplayForecast(Integer weatherToday, Integer weatherTomorrow, Long currentTime) {
        this.weatherToday = weatherToday;
        this.weatherTomorrow = weatherTomorrow;
        this.currentTime = currentTime;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getWeatherToday() {
        return weatherToday;
    }

    public void setWeatherToday(Integer weatherToday) {
        this.weatherToday = weatherToday;
    }

    public Integer getWeatherTomorrow() {
        return weatherTomorrow;
    }

    public void setWeatherTomorrow(Integer weatherTomorrow) {
        this.weatherTomorrow = weatherTomorrow;
    }

    @JsonProperty("fact")
    private void unpackFactTempFromNestedObject(Map<String, Object> fact){
        weatherToday = (Integer)fact.get("temp");
    }

    @JsonProperty("forecasts")
    private void unpackForecastTempFromNestedObject(Object[] forecasts){

        @SuppressWarnings("unchecked")
        Map<String, Object> tomorrowForecast = (Map<String, Object>)forecasts[1];

        @SuppressWarnings("unchecked")
        Map<String, Object> parts = (Map<String, Object>) tomorrowForecast.get("parts");

        @SuppressWarnings("unchecked")
        Map<String, Object> morning = (Map<String, Object>) parts.get("morning");

        weatherTomorrow = (Integer) morning.get("temp_avg");

    }

    @Override
    public String toString() {
        return "DisplayForecast{" +
                "weatherToday=" + weatherToday +
                ", weatherTomorrow=" + weatherTomorrow +
                ", currentTime=" + currentTime +
                '}';
    }
}
