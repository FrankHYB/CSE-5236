package com.example.course.easylease;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class House {
    private double latitude;
    private double longitude;
    private String address;

    private String name;
    private String description;
    private int price;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private void getLatLng(Context context) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = geocoder.getFromLocationName(address, 1);
        this.latitude = addressList.get(0).getLatitude();
        this.longitude = addressList.get(0).getLongitude();
    }
}
