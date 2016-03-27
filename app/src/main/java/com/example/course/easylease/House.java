package com.example.course.easylease;

public class House {
    private double latitude;
    private double longitude;
    private String address;
    private String zipCode;
    private String name;
    private String description;
    private int price;

    public House(String address,String zipCode,String name,String description,int price){
        this.address= address;
        this.zipCode = zipCode;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public House(){
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
}
