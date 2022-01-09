package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Year;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categories.getItems().removeAll(categories.getItems());
        //fields of books
        categories.getItems().addAll(Category.Science,Category.Art,Category.Geography,Category.History,Category.Religion);
        //categories.getItems().addAll("Science", "Art", "Religion","History","Geography");
        //default field;
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
            System.out.println("error occurred");
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

        }catch (Exception e){
            //show a warning
        }
        //TODO query to add book

    }
    @FXML
    Boolean addBookToTable(Book book){
        Connection connection =utils.getConnection();
        try{
            String query = "INSERT INTO BOOK(ISBN,Title,Publisher_Name,Publication_year,SellingPrice,Category,Min_Quantity,In_Stock) VALUES(?,?,?,?,?,?,?,?)";
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
            connection.commit();


        }catch (SQLException e){
            System.out.println("error occurred");
            e.printStackTrace();
        }
        return true;
    }
    @FXML
    Boolean addAuthorsToTable(){
        return true;
    }
}
