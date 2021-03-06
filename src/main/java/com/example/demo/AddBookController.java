package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
    static Stage stage;
    @FXML
    ComboBox<Category> categories;
    @FXML
    TextField title;
    @FXML
    TextField publisher;
    @FXML
    TextField authors;
    @FXML
    TextField ISBN;
    @FXML
    TextField publication;
    @FXML
    TextField year;
    @FXML
    TextField sellingPrice;
    @FXML
    TextField quantity;
    @FXML
    TextField inStock;
    @FXML
    Label warning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categories.getItems().removeAll(categories.getItems());
        categories.getItems().addAll(Category.Science,Category.Art,Category.Geography,Category.History,Category.Religion);
        categories.getSelectionModel().select(Category.Science);
    }
    @FXML
    void addBook(){
        if(publication.getText().isBlank()
        ||title.getText().isBlank()
        ||ISBN.getText().isBlank()||
        year.getText().isBlank()||
        sellingPrice.getText().isBlank()
        ||authors.getText().isBlank()||
        publisher.getText().isBlank()){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("complete data");
            a.show();
            return;
        }
        if(!Validator.validateISBN(ISBN.getText())){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("check ISBN format");
            a.show();
            return;
        }
        try{

            int quantityInt= Integer.parseInt(quantity.getText());
            double price=Double.parseDouble(sellingPrice.getText());
            int inStockint = Integer.parseInt(inStock.getText());
            Book book = new Book(
                    ISBN.getText(),
                    title.getText(),
                    categories.getSelectionModel().getSelectedItem(),
                    year.getText(),
                    price ,
                    publisher.getText(),
                    inStockint,
                    quantityInt);
        if(addBookToTable(book)&&addAuthorsToTable()){
            utils.getConnection().commit();
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("book is added");
            a.show();
            stage.close();

        }else{
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("book is not added");
            a.show();
            utils.getConnection().rollback();
            System.out.println("check data");
        }

        }catch (Exception e){
            e.printStackTrace();

        }

    }
    @FXML
    Boolean addBookToTable(Book book){
        Connection connection =utils.getConnection();
        try{
            String query = "INSERT INTO BOOK(ISBN,Title,Publisher_Name,Publication_year,Selling_Price,Category,Min_Quantity,In_Stock) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,book.getISBN());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3,book.getPublisher());
            preparedStatement.setString(4,book.getPublication_year());
            preparedStatement.setDouble(5,book.getSellingPrice());
            preparedStatement.setString(6,book.getCategory()+"");
            preparedStatement.setInt(7,book.getMinQuantity());
            preparedStatement.setInt(8,book.getInStock());
            preparedStatement.execute();


        }catch (SQLException e){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("check your data");
            a.show();
            e.printStackTrace();
        }
        return true;
    }

    Boolean addAuthorsToTable(){
        String array[] =authors.getText().split(",");
        Connection connection= utils.getConnection();
        try{
            String query = "INSERT INTO AUTHOR(ISBN,Author_Name)VALUES(?,?)";
            for (int  i = 0 ; i < array.length;i++){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, ISBN.getText());
                preparedStatement.setString(2,array[i]);
                preparedStatement.execute();
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
