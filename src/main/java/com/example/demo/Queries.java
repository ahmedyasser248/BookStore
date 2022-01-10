package com.example.demo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class Queries {
    // TODO in case of multiple authors,what to do
    static ArrayList<Book> searchByCategory(String categoryName, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,"
                + "b.category ,b.Min_Quantity,b.In_Stock ,b.ISBN ,b.Selling_Price "
                + " from BOOK as b, AUTHOR as a"
                + " where b.category = "+"?"+ " And b.ISBN = a.ISBN;");
            getBooksByCategory.setString(1,categoryName);
            ResultSet  result = getBooksByCategory.executeQuery();
            ArrayList<Book> output = new ArrayList<>();
            while (result.next()){
                output.add(
                    new Book(result.getString("Title"),
                             result.getString("Author_Name"),
                             Category.valueOf(result.getString("Category")),
                             result.getInt("Min_Quantity"),
                             result.getInt("In_Stock"),
                             result.getString("ISBN"),
                             result.getDouble("Selling_Price")));
            }
            return mergeAuthors(output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static ArrayList<Book> searchByAuthor(String Author, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,"
                + "b.category ,b.Min_Quantity,b.In_Stock ,b.ISBN ,b.Selling_Price "
                + " from BOOK as b, AUTHOR as a"
                + " where a.Author_Name = "+"?"+ " And b.ISBN = a.ISBN;");
            getBooksByCategory.setString(1,Author);
            ResultSet  result = getBooksByCategory.executeQuery();
            ArrayList<Book> output = new ArrayList<>();
            while (result.next()){
                output.add(
                    new Book(result.getString("Title"),
                        result.getString("Author_Name"),
                        Category.valueOf(result.getString("Category")),
                        result.getInt("Min_Quantity"),
                        result.getInt("In_Stock"),
                        result.getString("ISBN"),
                        result.getDouble("Selling_Price")));
            }
            return mergeAuthors(output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static ArrayList<Book> searchByPublisher(String Author, Connection conn){
        try {
            PreparedStatement getBooksByCategory
                = conn.prepareStatement("select b.Title,a.Author_Name,b.category "
                + ",b.Min_Quantity,b.In_Stock ,b.ISBN,b.Selling_Price "
                + " from BOOK as b, AUTHOR as a"
                + " where b.Publisher_Name = "+"?"+ " And b.ISBN = a.ISBN;");
            getBooksByCategory.setString(1,Author);
            ResultSet  result = getBooksByCategory.executeQuery();
            ArrayList<Book> output = new ArrayList<>();
            while (result.next()){
                output.add(
                    new Book(result.getString("Title"),
                        result.getString("Author_Name"),
                        Category.valueOf(result.getString("Category")),
                        result.getInt("Min_Quantity"),
                        result.getInt("In_Stock"),
                        result.getString("ISBN"),
                        result.getDouble("Selling_Price")));
            }
            return mergeAuthors(output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static ArrayList<Book> search(String searchTerm ,Connection conn,String searchCriteria){
        if(searchCriteria.equals("ISBN")||searchCriteria.equals("Title")){
            return searchISBNorAuthor(searchTerm,conn);
        }else if(searchCriteria.matches("Category")){
            return searchByCategory(searchTerm,conn);
        }else if(searchCriteria.matches("Author")){
            return searchByAuthor(searchTerm,conn);
        }else if(searchCriteria.equals("Publisher")){
            return searchByPublisher(searchTerm,conn);
        }
        return null;
    }


     static ArrayList<Book> searchISBNorAuthor(String searchTerm,Connection conn){
        String equalityCond = "";
        if(searchTerm.matches("[0-9]{3}-[0-9]{2}-[0-9]{5}-[0-9]{2}-[0-9]")){
            equalityCond += " where b.ISBN = ";
        }else{
            equalityCond += " where b.Title = ";
        }
        /*if(searchCriteria == 0){
            equalityCond += " where b.ISBN = ";
        }else if(searchCriteria == 1){
            equalityCond += " where b.Title = ";
        }*/
         try {
             String query = "select b.Title,a.Author_Name,b.Category,b.ISBN "
                 + ",b.Min_Quantity,b.In_Stock ,b.ISBN ,b.Selling_Price "
                 + "from BOOK as b , AUTHOR as a"
                 + equalityCond+"?" + " AND b.ISBN = a.ISBN;";
             PreparedStatement getBooks = conn.prepareStatement(query);
             getBooks.setString(1,searchTerm);
             System.out.println(getBooks.toString());
             ResultSet result = getBooks.executeQuery();
             ArrayList<Book> output = new ArrayList<>();


             while (result.next()){

                 output.add(
                     new Book(result.getString("Title"),
                         result.getString("Author_Name"),
                         Category.valueOf(result.getString("Category")),
                         result.getInt("Min_Quantity"),
                         result.getInt("In_Stock"),
                         result.getString("ISBN"),
                         result.getDouble("Selling_Price")));

             }
             return mergeAuthors(output);
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
         return null;
     }

     static void confirmOrder(String ISBN,Connection conn){


         try {
             String query = "Delete from Book_Order where ISBN = ?";
             PreparedStatement deleteOrder = conn.prepareStatement(query);
             deleteOrder.setString(1,ISBN);
             deleteOrder.execute();
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
     }

     static ArrayList<BookOrders> getBookOrders(Connection conn){
         ArrayList<BookOrders> bookOrdersArray = new ArrayList<>();
         try {
             String query = "select * "
                 + "from Book_Order";
             PreparedStatement getBookOrders = conn.prepareStatement(query);
             ResultSet bookOrders = getBookOrders.executeQuery();
             while (bookOrders.next()){
                 bookOrdersArray.add(new BookOrders(bookOrders.getString("ISBN"),
                     bookOrders.getString("Quantity_Ordered"),
                     bookOrders.getTimestamp("Order_Date")));
             }


         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
         return bookOrdersArray;
     }

     static void promoteUser(String username,Connection conn){
         try {
             String query = "Update CUSTOMER "
                 + "set Manager = 1 where Username = ?";
             PreparedStatement updateUserStatus = conn.prepareStatement(query);
             updateUserStatus.setString(1,username);
             updateUserStatus.execute();
             conn.commit();
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
     }

     static boolean userFound(String username,Connection conn){
         try {
             String query = "Select Username "
                 + "from CUSTOMER where Username = ?";
             PreparedStatement getStatus = conn.prepareStatement(query);
             getStatus.setString(1,username);

             ResultSet status = getStatus.executeQuery();
             //String result = status.getString("Username");
             conn.commit();
             return status.next();

         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
         return false;
     }

     static public ArrayList<Book> mergeAuthors(ArrayList<Book> books){
         for (int i = 1; i < books.size(); i++) {
             if(books.get(i).getISBN().equals(books.get(i-1).getISBN())){
                 Book book1 = books.get(i-1);
                 Book book2 = books.remove(i);
                 String authors = book1.author + " , " + book2.author;
                 book1.setAuthor(authors);
                 books.set(i-1,book1);
                 i--;
             }
         }
         return books;
     }

    public static void main(String[] args) {

        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/bookstore";
            //Class.forName(myDriver);
            Connection conn = null;
            conn = DriverManager.getConnection(myUrl, "root", "Soraislife443");
            ArrayList<Book> result = search("",conn,null);
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
