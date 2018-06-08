package com.mir.newgift.model;

import java.util.ArrayList;

public class Product {
    private Integer id;
    private String name;
    private Float price;
    private ArrayList<Integer> features1;
    private ArrayList<Integer> features2;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    public void setFeatures1(ArrayList<Integer> features1) {
        this.features1 = features1;
    }

    public void setFeatures2(ArrayList<Integer> features2) {
        this.features2 = features2;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public ArrayList<Integer> getFeatures1() {
        return features1;
    }

    public ArrayList<Integer> getFeatures2() {
        return features2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
