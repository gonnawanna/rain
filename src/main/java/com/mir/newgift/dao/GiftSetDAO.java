package com.mir.newgift.dao;

import com.mir.newgift.model.GiftSet;
import com.mir.newgift.model.Product;
import com.mir.newgift.model.SimpleGiftSet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class GiftSetDAO {

    private static Connection connection;

    static {
        String url = "";
        String username = "";
        String password = "";

        try(InputStream in = GiftSetDAO.class.getClassLoader().getResourceAsStream("persistence.properties")) {

            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void updateGiftSetsData (ArrayList<GiftSet> giftSets) throws SQLException {
        truncateGiftSetTables();
        int id = 1;
        for (GiftSet giftSet : giftSets) {
            insertGiftSet(id, giftSet.getName(), giftSet.getPrice());

            insertGiftSetsFeatures(id, giftSet.getFeatures1(), "set_feature_1 (set_id, feature_1_id)");
            insertGiftSetsFeatures(id, giftSet.getFeatures2(), "set_feature_2 (set_id, feature_2_id)");

            ArrayList<Product> productsInGiftSet = giftSet.getProducts();
            for (Product product: productsInGiftSet) {
                insertGiftSetProduct(id, product.getId());
            }
            id++;
        }
    }

    private void truncateGiftSetTables () throws SQLException {
        String SQLQueryTruncateTables = "TRUNCATE TABLE set_feature_1;\n" +
                "TRUNCATE TABLE set_feature_2; TRUNCATE TABLE sets_products; TRUNCATE TABLE sets;";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(SQLQueryTruncateTables);
    }

    private void insertGiftSet (Integer id, String name, float price) throws SQLException {
        String SQLQueryInsertGiftSetRow = "INSERT INTO sets (set_id, name, price) VALUES (?,?,?);";
        PreparedStatement ps = connection.prepareStatement(SQLQueryInsertGiftSetRow);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setFloat(3, price);
        ps.executeUpdate();
    }

    private void insertGiftSetsFeatures (Integer id, ArrayList<Integer> features, String tableName) throws SQLException {
        String SQLQueryInsertGiftSetFeatureRow = "INSERT INTO " + tableName + " VALUES (?,?);";
        for (Integer feature : features) {
            PreparedStatement ps = connection.prepareStatement(SQLQueryInsertGiftSetFeatureRow);
            ps.setInt(1, id);
            ps.setInt(2, feature);
            ps.executeUpdate();
        }
    }

    private void insertGiftSetProduct (Integer giftSetId, Integer productId) throws SQLException {
        String SQLQueryInsertGiftSetProductRow = "INSERT INTO sets_products (set_id, product_id) VALUES (?,?);";
        PreparedStatement ps = connection.prepareStatement(SQLQueryInsertGiftSetProductRow);
        ps.setInt(1, giftSetId);
        ps.setInt(2, productId);
        ps.executeUpdate();
    }

    public ArrayList<Integer> selectGiftSetsIds(String gender, String age, String category1, String category2, String category3)
            throws SQLException {
        ArrayList<Integer> giftSetsIds = selectGiftSetsIdsByFeatures(gender, age);
        if (!category1.equals("")) {
            giftSetsIds.retainAll(selectGiftSetsIdsByCategory(category1));
        }
        if (!category2.equals("")) {
            giftSetsIds.retainAll(selectGiftSetsIdsByCategory(category2));
        }
        if (!category3.equals("")) {
            giftSetsIds.retainAll(selectGiftSetsIdsByCategory(category3));
        }
        return giftSetsIds;
    }

    public ArrayList<SimpleGiftSet> selectAllById (ArrayList<Integer> giftSetsIds, String order) throws SQLException {
        String SQLQuerySelectGiftSet = "SELECT name, price\n" +
                "FROM sets WHERE set_id = ?";
        ArrayList<SimpleGiftSet> simpleGiftSets = new ArrayList<>();
        for (Integer giftSetId : giftSetsIds) {
            PreparedStatement ps = connection.prepareStatement(SQLQuerySelectGiftSet);
            ps.setInt(1, giftSetId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                float price = rs.getFloat(2);

                SimpleGiftSet simpleGiftSet = new SimpleGiftSet();
                simpleGiftSet.setName(name);
                simpleGiftSet.setPrice(price);
                simpleGiftSets.add(simpleGiftSet);
            }
        }
        if (order.equals("ASC")) {
            //Collections.sort(simpleGiftSets);
        }
        return simpleGiftSets;
    }

    private ArrayList<Integer> selectGiftSetsIdsByFeatures(String gender, String age) throws SQLException {
        String SQLQuerySelectGiftSetsByFeatures = "SELECT DISTINCT sets.set_id FROM sets \n" +
                "INNER JOIN set_feature_1 ON sets.set_id = set_feature_1.set_id\n" +
                "INNER JOIN set_feature_2 ON sets.set_id = set_feature_2.set_id\n" +
                "INNER JOIN feature_1 ON feature_1.feature_1_id = set_feature_1.feature_1_id\n" +
                "INNER JOIN feature_2 ON feature_2.feature_2_id = set_feature_2.feature_2_id\n" +
                "WHERE feature_1.feature_value = ? AND feature_2.feature_value = ?;";
        PreparedStatement ps = connection.prepareStatement(SQLQuerySelectGiftSetsByFeatures);
        ps.setString(1, gender);
        ps.setString(2, age);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> giftSetsIds = new ArrayList<>();

        while (rs.next()) {
            Integer giftSetId = rs.getInt(1);
            giftSetsIds.add(giftSetId);
        }
        return giftSetsIds;
    }

    private ArrayList<Integer> selectGiftSetsIdsByCategory(String category) throws SQLException {
        String SQLQuerySelectGiftSetsByCategory = "SELECT DISTINCT sets.set_id\n" +
                "FROM sets INNER JOIN sets_products \n" +
                "ON sets.set_id = sets_products.set_id\n" +
                "INNER JOIN products \n" +
                "ON sets_products.product_id = products.product_id \n" +
                "INNER JOIN categories\n" +
                "ON products.category_id = categories.category_id\n" +
                "WHERE category_name = ?";
        PreparedStatement ps = connection.prepareStatement(SQLQuerySelectGiftSetsByCategory);
        ps.setString(1, category);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> giftSetsIds = new ArrayList<>();

        while (rs.next()) {
            Integer giftSetId = rs.getInt(1);
            giftSetsIds.add(giftSetId);
        }
        return giftSetsIds;
    }

}
