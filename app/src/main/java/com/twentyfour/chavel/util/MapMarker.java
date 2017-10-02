package com.twentyfour.chavel.util;

import java.util.ArrayList;

/**
 * Created by barbara on 4/24/16.
 */
public class MapMarker {
    private int id;
    private double lat;
    private double lng;
    private String description;

    // TODO, make async
    public static ArrayList<MapMarker> getMapMarkers() {
        ArrayList<MapMarker> list = new ArrayList<MapMarker>();
        list.add(new MapMarker(1, 13.76408354, 100.78441142, "SEA-TAC Airport"));

        return list;
    }

    public MapMarker(int id, double lat, double lng, String description) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
