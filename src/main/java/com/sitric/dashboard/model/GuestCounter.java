package com.sitric.dashboard.model;

/**
 * GuestCounter POJO class as MongoDB entity
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "guestCounter")
public class GuestCounter {

    @Id
    private String id;

    @Field(value = "counter")
    private int counter;


    public GuestCounter() {
    }

    public GuestCounter(String id, int counter) {
        this.id = id;
        this.counter = counter;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


    @Override
    public String toString() {
        return "GuestCounter{" +
                "id=" + id +
                ", counter=" + counter +
                '}';
    }
}
