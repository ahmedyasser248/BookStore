package com.example.demo;

import java.sql.Connection;
import java.sql.*;

public class Queries {
    Book[] searchByCategory(String categoryName, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,b.category"
                + "from book as b, author as a"
                + "where b.category ="+categoryName+ "And b.ISBN = a.ISBN;");
            ResultSet  result = getBooksByCategory.executeQuery();
            int size = result.getFetchSize();
            Book[] output = new Book[size];
            int i = 0;
            while (result.next()){
                output[i] = new Book(result.getString("Title"),
                    result.getString("Author_Name"),
                    result.getString("Category"));
                i++;
            }
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}
