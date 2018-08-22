package com.sitric.dashboard.model;

/**
 * GuestCounter POJO class as MongoDB entity
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Document(collection = "GuestCounter")
public class GuestCounter {

    @Id
    private Long id;

    @Field(value = "counter")
    private Long counter;

    @Field(value = "current_date")
    private Date currentDate;

    public GuestCounter() {
    }

    public GuestCounter(Long id, Long counter, Date currentDate) {
        this.id = id;
        this.counter = counter;
        this.currentDate = currentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public String toString() {
        return "GuestCounter{" +
                "id=" + id +
                ", counter=" + counter +
                ", currentDate=" + currentDate +
                '}';
    }
}
