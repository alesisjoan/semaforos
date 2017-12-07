package com.example.alesis.myapplication.model;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alesis on 21/08/2016.
 */
public class Semaforo implements Serializable{
    private Long id = new Date().getTime(); // TODO deberia ser calle y orden
    private Long timePeriod = 0l;
    private Long greenStart = 0l;
    private Long greenStartAgain = 0l;
    private Location location;
    private String streetName;
    private int order;
    private boolean processed = false;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Long getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Long timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Long getGreenStart() {
        return greenStart;
    }

    public void setGreenStart(Long greenStart) {
        this.greenStart = greenStart;
    }

    public Long getGreenStartAgain() {
        return greenStartAgain;
    }

    public void setGreenStartAgain(Long greenStartAgain) {
        if(greenStart>0l){
            Long diff = greenStartAgain - greenStart;
            Long roundedNumber = (diff + 500) / 1000 * 1000;
            this.setTimePeriod(roundedNumber);
        }
        this.greenStartAgain = greenStartAgain;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Long nextGreen(){
        Long period = this.timePeriod;
        Long greenStart = this.getGreenStart();
        Long now = System.currentTimeMillis();
        Long longDiff = now - greenStart;
        Long timesNextGreen = longDiff / period;
        timesNextGreen += 1l;
        return greenStart + (period * timesNextGreen);
    }

    public int getTimeToGreen(){
        Long    now = System.currentTimeMillis();
        Date    dateNextGreen = new Date(this.nextGreen());
        Long	countDown = dateNextGreen.getTime() - now;
        countDown /= 1000l;
        return countDown.intValue();

    }

}
