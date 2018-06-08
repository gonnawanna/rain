package com.mir.newgift.core;

import com.mir.newgift.dao.CategoryDAO;
import com.mir.newgift.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class CategoryRepository {
    @Autowired
    private CategoryDAO categoryDAO;

    private static final String SQL_QUERY_SELECT_CATEGORIES = "SELECT category_name\n" +
            "FROM categories;";

    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories() throws SQLException {
        this.categories = categoryDAO.selectAll(SQL_QUERY_SELECT_CATEGORIES);
    }
}
