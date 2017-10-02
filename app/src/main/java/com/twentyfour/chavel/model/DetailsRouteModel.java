package com.twentyfour.chavel.model;

/**
 * Created by Bas on 9/13/2017 AD.
 */

public class DetailsRouteModel {
    String desc;
    String activity;
    String location;
    String travel;
    String period;
    String suggestion;
    String routeName;

    public DetailsRouteModel(){

    }

    public DetailsRouteModel(String desc, String activity, String location, String travel, String period, String suggestion, String routeName) {
        this.desc = desc;
        this.activity = activity;
        this.location = location;
        this.travel = travel;
        this.period = period;
        this.suggestion = suggestion;
        this.routeName = routeName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
