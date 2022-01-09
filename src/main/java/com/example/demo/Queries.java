package com.example.demo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class Queries {
    static ArrayList<Book> searchByCategory(Category categoryName, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,b.category "
                + " from book as b, author as a "
                + " where b.category ="+categoryName+ "And b.ISBN = a.ISBN;");
            ResultSet  result = getBooksByCategory.executeQuery();
            ArrayList<Book> output = new ArrayList<>();
            while (result.next()){
                output.add(new Book(result.getString("Title"),
                    result.getString("Author_Name"),
                    Category.valueOf(result.getString("Category"))));
            }
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static ArrayList<Book> search(String searchTerm, Connection conn,int searchCriteria){
        try {
            String equalityCond = "";
            if(searchCriteria == 0){
                equalityCond += " where b.Title = ?";
            }else if(searchCriteria == 1){
                equalityCond += " where b.ISBN = ?";
            }
            PreparedStatement getBooks
                = conn.prepareStatement("select b.Title,a.Author_Name,b.category "
                + " from book as b, author as a "
                + equalityCond+ " And b.ISBN = a.ISBN;");
            getBooks.setString(1,searchTerm);
            ResultSet  result = getBooks.executeQuery();

            ArrayList<Book> output = new ArrayList<>();

            while (result.next()){
               output.add(new Book(result.getString("Title"),
                    result.getString("Author_Name"),
                    Category.valueOf(result.getString("Category"))));

            }
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String myDriver = "org.gjt.mm.mysql.Driver";
        String myUrl = "jdbc:mysql://localhost/bookstore";
        //Class.forName(myDriver);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(myUrl, "root", "Soraislife443");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayList<Book> output = search("12345678",conn,1);
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i).toString());
        }
    }

}
