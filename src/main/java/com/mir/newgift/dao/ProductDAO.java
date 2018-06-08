package com.mir.newgift.dao;

import com.mir.newgift.model.Product;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class ProductDAO {

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

    public ArrayList<Product> selectAll (String SQLQuery) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQLQuery);
        ArrayList<Product> products = new ArrayList<>();

        while (rs.next()) {
            Product product = new Product();
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            float price = rs.getFloat(3);

            ArrayList<Integer> features1 = selectFeatures("feature_1_id", "product_feature_1", id);
            ArrayList<Integer> features2 = selectFeatures("feature_2_id", "product_feature_2", id);

            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setFeatures1(features1);
            product.setFeatures2(features2);
            products.add(product);
        }
        return products;
    }

    private ArrayList<Integer> selectFeatures (String columnName, String tableName, Integer productId) throws SQLException {
        String sqlQuerySelectFeaturesId = "SELECT " + columnName + " FROM " + tableName + " WHERE product_id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQuerySelectFeaturesId);
        ps.setInt(1, productId);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> features = new ArrayList<>();

        while (rs.next()) {
            Integer featureId = rs.getInt(1);
            features.add(featureId);
        }
        return features;
    }

}
