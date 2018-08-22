package com.sitric.dashboard.model;

/**
 * DisplayExchangeRates used for binding JSON from exchange rates informer and Java object
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.spring.annotation.SpringComponent;
import java.util.Map;


@SpringComponent
@JsonIgnoreProperties(ignoreUnknown=true)
public class DisplayExchangeRates {

    private Double USDRate;
    private Double EURRate;

    public DisplayExchangeRates() {
    }

    public Double getUSDRate() {
        return USDRate;
    }

    public void setUSDRate(Double USDRate) {
        this.USDRate = USDRate;
    }

    public Double getEURRate() {
        return EURRate;
    }

    public void setEURRate(Double EURRate) {
        this.EURRate = EURRate;
    }

    public DisplayExchangeRates(Double USDRate, Double EURRate) {
        this.USDRate = USDRate;
        this.EURRate = EURRate;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("Valute")
    private void unpackCurrencyValuesFromNestedObject(Map<String, Object> Valute){

        Map<String, Object> USD = (Map<String, Object>)Valute.get("USD");
        USDRate = (Double)USD.get("Value");

        Map<String, Object> EUR = (Map<String, Object>)Valute.get("EUR");
        EURRate = (Double)EUR.get("Value");
    }

    @Override
    public String toString() {
        return "DisplayExchangeRates{" +
                "USDRate=" + USDRate +
                ", EURRate=" + EURRate +
                '}';
    }
}
