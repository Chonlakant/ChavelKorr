package com.twentyfour.chavel.misc;

/**
 * Created by vittal on 12/2/17.
 */

public class Driver {

    private int id;
    private int driver_id;
    private double latitude;
    private double longitude;
    private String status;
    private String shift_status;
    private String update_date;

    public Driver() {
    }

    public int getId() {
        return id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStatus() {
        return status;
    }

    public String getShift_status() {
        return shift_status;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShift_status(String shift_status) {
        this.shift_status = shift_status;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
}
