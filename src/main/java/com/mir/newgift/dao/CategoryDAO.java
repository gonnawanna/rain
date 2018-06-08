package com.mir.newgift.dao;

import com.mir.newgift.model.Category;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class CategoryDAO {

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

    public ArrayList<Category> selectAll (String SQLQuery) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQLQuery);
        ArrayList<Category> categories = new ArrayList<>();

        while (rs.next()) {
            Category category = new Category();
            String name = rs.getString(1);

            category.setName(name);
            categories.add(category);
        }
        return categories;
    }
}
