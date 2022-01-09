package com.example.demo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class Queries {
    // TODO in case of multiple authors,what to do

    static ArrayList<Book> searchByCategory(String categoryName, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,b.category "
                + " from book as b, author as a"
                + " where b.category = "+"?"+ " And b.ISBN = a.ISBN;");
            getBooksByCategory.setString(1,categoryName);
            ResultSet  result = getBooksByCategory.executeQuery();
            ArrayList<Book> output = new ArrayList<>();
            while (result.next()){
                output.add( new Book(result.getString("Title"),
                    result.getString("Author_Name"),
                    Category.valueOf(result.getString("Category"))));
            }
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static ArrayList<Book> searchByAuthor(String categoryName, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,b.category "
                + " from book as b, author as a"
                + " where b.category = "+"?"+ " And b.ISBN = a.ISBN;");
            getBooksByCategory.setString(1,categoryName);
            ResultSet  result = getBooksByCategory.executeQuery();
            ArrayList<Book> output = new ArrayList<>();
            while (result.next()){
                output.add( new Book(result.getString("Title"),
                    result.getString("Author_Name"),
                    Category.valueOf(result.getString("Category"))));
            }
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


     static ArrayList<Book> searchByISBN(String ISBN,Connection conn,int searchCriteria){
        String equalityCond = "";
        if(searchCriteria == 0){
            equalityCond += " where b.ISBN = ";
        }else if(searchCriteria == 1){
            equalityCond += " where b.Title = ";
        }
         try {
             String query = "select b.Title,a.Author_Name,b.Category "
                 + "from book as b , author as a"
                 + equalityCond+"?" + " And b.ISBN = a.ISBN;";
             PreparedStatement getBooks = conn.prepareStatement(query);
             getBooks.setString(1,ISBN);
             System.out.println(getBooks.toString());
             ResultSet result = getBooks.executeQuery();
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

        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/bookstore";
            //Class.forName(myDriver);
            Connection conn = null;
            conn = DriverManager.getConnection(myUrl, "root", "Soraislife443");
            ArrayList<Book> result = searchByCategory("Science",conn);
            /*PreparedStatement getBooks = conn.prepareStatement("Select Title from book where ISBN = '12345678'");
            ResultSet output = getBooks.executeQuery();
            while (output.next()){
                System.out.println(output.getString("Title"));
            }*/
            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i).toString());
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
