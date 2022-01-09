package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ModifyBookController implements Initializable {
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
        ArrayList<Book> arr= Queries.searchByISBN(searchBox.getText(),utils.getConnection(),0);
        if(arr!=null &&arr.get(0)!=null){
            Book book = arr.get(0);

        }
        Book book =arr.get(0);

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
