package com.example.course.easylease;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class House {
    private double latitude;
    private double longitude;
    private String address;
    private String zipCode;
    private String name;
    private String description;
    private String owner;
    private Context appcontext;
    private int price;
    private int rooms;

    public House(String address, String zipCode, String name, String description, int price,int rooms ,String owner,Context context) {
        this.address = address;
        this.zipCode = zipCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rooms=rooms;
        this.owner=owner;
        appcontext = SingletonContext.getInstance(context);
        try {
            getLatLng(context);
        } catch (IOException e) {
        }
    }

    public House() {
    }

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

        try {
            getLatLng(appcontext);
        } catch (IOException e) {
        }
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getPrice() {
        return Integer.toString(price);
    }
    public String getRooms(){
        return Integer.toString(rooms);
    }
    public String getOwner(){
        return this.owner;
    }
    public void setOwner(String owner){
        this.owner = owner;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setRooms(int room) {this.rooms = room;}

    @Override
    public String toString() {
        return "{" +
                "latitude:" + latitude +
                ", longitude:" + longitude +
                ", address:'" + address + '\'' +
                ", zipCode:'" + zipCode + '\'' +
                ", name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", price:" + price +
                ", rooms:" + rooms +
                ", owner:'" + owner + '\'' +
                '}';
    }
    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public void setAppcontext(Context appcontext) {
        this.appcontext = SingletonContext.getInstance(appcontext);
    }

    private void getLatLng(Context context) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = geocoder.getFromLocationName(address, 1);
        this.latitude = addressList.get(0).getLatitude();
        this.longitude = addressList.get(0).getLongitude();
    }
}
