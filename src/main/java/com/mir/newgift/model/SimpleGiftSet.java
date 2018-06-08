package com.mir.newgift.model;

public class SimpleGiftSet {

    private String name;
    private float price;

    public SimpleGiftSet() {};

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
