package com.example.course.easylease;

/**
 * Created by yubin on 3/26/16.
 */
public class House {
    private String name;
    private int price;
    private String address;
    private String description;
    public House(String name,int price,String address){
        this.name=name;
        this.price=price;
        this.address=address;
    }
    public void SetDescription(String des){
        this.description=des;
    }
    public String getName(){
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
    public String getDescription(){
        return description;
    }



}
