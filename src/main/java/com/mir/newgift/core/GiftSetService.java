package com.mir.newgift.core;

import com.mir.newgift.dao.GiftSetDAO;
import com.mir.newgift.model.GiftSet;
import com.mir.newgift.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class GiftSetService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private GiftSetDAO giftSetDAO;

    public void main () throws SQLException {
        repository.setProducts();
        repository.setExcessReserves();
        ArrayList<Product> products = repository.getProducts();
        ArrayList<Product> excessReserves = repository.getExcessReserves();
        ArrayList<GiftSet> giftSets = createGiftSets(products, excessReserves);
        giftSetDAO.updateGiftSetsData(giftSets);
    }

    private ArrayList<GiftSet> createGiftSets (ArrayList<Product> products, ArrayList<Product> excessReserves) {
        ArrayList<GiftSet> giftSets = new ArrayList<>();
        for (Product product1 : excessReserves) {
            for (Integer feature1 : product1.getFeatures1()) {
                for (Integer feature2 : product1.getFeatures2()) {
                    ArrayList<Product> suitableProducts = selectSuitableProducts(products, product1, feature1, feature2);
                    ArrayList<GiftSet> newGiftSets = collectGiftSets(product1, suitableProducts);
                    for (GiftSet newGiftSet : newGiftSets) {

                        if (giftSets.contains(newGiftSet)) {
                            int i = giftSets.indexOf(newGiftSet);
                            GiftSet giftSet = giftSets.get(i);
                            giftSet.addFeature1(feature1);
                            giftSet.addFeature2(feature2);

                        } else {
                            giftSets.add(newGiftSet);
                            newGiftSet.addFeature1(feature1);
                            newGiftSet.addFeature2(feature2);
                        }
                    }
                }
            }
        }
        return giftSets;
    }

    private ArrayList<Product> selectSuitableProducts (ArrayList<Product> products, Product product, Integer feature1, Integer feature2) {
        ArrayList<Product> suitableProducts = new ArrayList<>();
        for (Product anotherProduct : products) {
            if (anotherProduct.getFeatures1().contains(feature1) & anotherProduct.getFeatures2().contains(feature2)) {
                if (!anotherProduct.equals(product)) {
                    suitableProducts.add(anotherProduct);
                }
            }
        }
        return suitableProducts;
    }

    private ArrayList<GiftSet> collectGiftSets (Product product1, ArrayList<Product> suitableProducts){
        ArrayList<GiftSet> giftSets = new ArrayList<>();
        for (int i = 0; i < suitableProducts.size(); i++) {
            for (int j = i + 1; j < suitableProducts.size(); j++) {
                Product product2 = suitableProducts.get(i);
                Product product3 = suitableProducts.get(j);
                GiftSet giftSet = new GiftSet(product1, product2, product3);
                giftSets.add(giftSet);
            }
        }
        return giftSets;
    }
}
