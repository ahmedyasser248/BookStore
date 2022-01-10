package com.example.demo;

import java.sql.*;

public class CartAccess {

    private static int getSoldQuantityInCurrentSession(Book book,Connection connection){
        int quantity=0;
        String query="SELECT * FROM Book_Sale AS Count WHERE ISBN = ? "+
                " AND Customer_Username = ? "+
                " And Session_Start_Date = ? ";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,book.getISBN());
            preparedStatement.setString(2,utils.getCurrentUsername());
            preparedStatement.setTimestamp(3,utils.getTransactionStartDate());
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                quantity=resultSet.getInt("Sold_Quantity");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    private static void updateQuantityInStock(Book book,int newQuantity,Connection connection){
        String query="UPDATE BOOK SET In_Stock="+(newQuantity)
                +" WHERE ISBN="+"'"+book.getISBN()+"'";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //TODO Check if the quantity has dropped below zero.
            //TODO Cannot rollback because other items in that were previously added to the cart would be deleted.
            //TODO If the quantity reached zero remove the book from the thing.

            //TODO place only one order if the quantity dropped below the given threshold.
            e.printStackTrace();
        }
    }

    private static void insertIntoBookSale(Book book,Timestamp saleDate,Connection connection){
        String query="INSERT INTO Book_Sale VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,book.getISBN());
            preparedStatement.setString(2,utils.getCurrentUsername());
            preparedStatement.setInt(3,1);
            preparedStatement.setTimestamp(4,saleDate);//TODO Use sql Timestamp class in GUI.
            preparedStatement.setTimestamp(5,utils.getTransactionStartDate());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateQuantityInBookSale(Book book,int newQuantity,Connection connection){
        String query="UPDATE Book_Sale SET Sold_Quantity = ? " +
                "WHERE ISBN = ? AND Customer_Username = ? AND Session_Start_Date = ?";


        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,newQuantity);
            preparedStatement.setString(2,book.getISBN());
            preparedStatement.setString(3,utils.getCurrentUsername());
            preparedStatement.setTimestamp(4,utils.getTransactionStartDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBookToCart(Book book, Timestamp saleDate, Connection connection){

        //Update the quantity in stock (decrease).
        updateQuantityInStock(book,book.getInStock()-1,connection);

        //Update the Book_Sale and add a record corresponding to the bought book.
        //Check if the user has already bought a book with the same ISBN during the current session.
        //TODO When the user commits get rid of the session start date I guess

        int quantity=getSoldQuantityInCurrentSession(book,connection);

        if(quantity==0){//Insert a new record into Book_Sale
            insertIntoBookSale(book,saleDate,connection);
        }
        else {//Increase the quantity of the book in Book_Sale
            updateQuantityInBookSale(book,quantity+1,connection);

        }

        book.setInStock(book.getInStock()-1);//Update the book quantity


    }

    private static void deleteFromBookSale(Book book,Connection connection){
        String query="DELETE FROM Book_Sale WHERE ISBN = ? AND Customer_Username = ? AND Session_Start_Date = ?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,book.getISBN());
            preparedStatement.setString(2,utils.getCurrentUsername());
            preparedStatement.setTimestamp(3,utils.getTransactionStartDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeBookFromCart(Book book,Connection connection){//TODO User must check the book that he wants to delete from cart
        //Update the quantity in stock (increase).
        updateQuantityInStock(book,book.getInStock()+1,connection);

        //Check the quantity that the user has bought from that book

        int quantity=getSoldQuantityInCurrentSession(book,connection);
        //If there is only one copy of the book was bought then delete the record from Book_Sale
        if (quantity==1){
            deleteFromBookSale(book,connection);
        }
        else {//Else decrease the quantity in Book_Sale
            updateQuantityInBookSale(book,quantity-1,connection);
        }
        book.setInStock(book.getInStock()+1);//Update the book quantity
    }
    public static void checkOut(Connection connection ){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}