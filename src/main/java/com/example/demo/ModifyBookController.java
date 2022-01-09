package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ModifyBookController implements Initializable {
    String isbn;
    @FXML
    Label warning;
    @FXML
    TextField searchBox;

    @FXML
    ComboBox<String> categories;
    @FXML
    TextField title;
    @FXML
    TextField inStockTf;
    @FXML
    TextField author;
    @FXML
    TextField publisher;
    @FXML
    TextField publication;
    @FXML
    TextField year;
    @FXML
    TextField sellingPrice;
    @FXML
    TextField quantity;
    @FXML
    void search(){
        String query = "select * "
                + "from book as b , author as a"
                + "WHERE b.ISBN="+"?" + " And b.ISBN = a.ISBN;";
        Connection connection = utils.getConnection();
        PreparedStatement getBooks = null;
        try {
            getBooks = connection.prepareStatement(query);
            getBooks.setString(1,searchBox.getText());
            ResultSet result = getBooks.executeQuery();

            if(!result.next()){
                //not found
            }else{
                String isbn = result.getString("ISBN");
                String authors = result.getString("Author_Name");
                String year = result.getString("Publication_Year");
                String title = result.getString("Title");
                String Publisher_Name = result.getString("Publisher_Name");
                Category category= Category.valueOf(result.getString("Category"));
                int minQuantity = result.getInt("Min_Quantity");
                int In_Stock = result.getInt("In_Stock");
                while (result.next()){
                    authors+=","+result.getString("Author_Name");
                }



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    @FXML
    void modifyBook(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categories.getItems().removeAll(categories.getItems());
        categories.getItems().addAll("Science", "Art", "Religion","History","Geography");

    }
}
