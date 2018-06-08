package com.mir.newgift.model;

import java.util.ArrayList;

public class GiftSet extends SimpleGiftSet {

    private ArrayList<Product> products;
    private ArrayList<Integer> features1;
    private ArrayList<Integer> features2;

    public GiftSet(Product firstProduct, Product secondProduct, Product thirdProduct) {
        super();
        setName(firstProduct.getName() + " + " + secondProduct.getName() + " + " + thirdProduct.getName());
        setPrice(firstProduct.getPrice() + secondProduct.getPrice() + thirdProduct.getPrice());
        this.products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);
        products.add(thirdProduct);
        this.features1 = new ArrayList<>();
        this.features2 = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Integer> getFeatures1() {
        return features1;
    }

    public ArrayList<Integer> getFeatures2() {
        return features2;
    }

    public void addFeature1 (Integer featureValue) {
        features1.add(featureValue);
    }

    public void addFeature2 (Integer featureValue) {
        features2.add(featureValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftSet giftSet = (GiftSet) o;

        return products != null ? products.containsAll(giftSet.products) : giftSet.products == null;
    }
}
