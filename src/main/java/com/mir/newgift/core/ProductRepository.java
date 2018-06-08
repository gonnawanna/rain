package com.mir.newgift.core;

import com.mir.newgift.dao.ProductDAO;
import com.mir.newgift.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class ProductRepository {
    @Autowired
    private ProductDAO productDAO;

    private static final String SQL_QUERY_SELECT_PRODUCTS = "SELECT product_id, name, price\n" +
            "FROM products;";
    private static final String SQL_QUERY_SELECT_EXCESS_RESERVE = "SELECT products.product_id, name, price, category_id\n" +
            "FROM products INNER JOIN excess_reserve\n" +
            "ON products.product_id = excess_reserve.product_id;";

    private ArrayList<Product> products;
    private ArrayList<Product> excessReserves;

    public void setProducts() throws SQLException {
        this.products = productDAO.selectAll(SQL_QUERY_SELECT_PRODUCTS);
    }

    public void setExcessReserves() throws SQLException {
        this.excessReserves = productDAO.selectAll(SQL_QUERY_SELECT_EXCESS_RESERVE);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getExcessReserves() {
        return excessReserves;
    }
}
