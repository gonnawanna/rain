package com.mir.newgift.core;

import com.mir.newgift.dao.GiftSetDAO;
import com.mir.newgift.model.Product;
import com.mir.newgift.model.SimpleGiftSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class SimpleGiftSetRepository {
    @Autowired
    private GiftSetDAO giftSetDAO;

    private ArrayList<SimpleGiftSet> simpleGiftSets;

    public ArrayList<SimpleGiftSet> getSimpleGiftSets() {
        return simpleGiftSets;
    }

    public void setSimpleGiftSets(String gender, String age, String order, String category1, String category2, String category3)
            throws SQLException{
        ArrayList<Integer> giftSetsIds = giftSetDAO.selectGiftSetsIds(gender, age, category1, category2, category3);
        this.simpleGiftSets = giftSetDAO.selectAllById(giftSetsIds, order);
    }
}
