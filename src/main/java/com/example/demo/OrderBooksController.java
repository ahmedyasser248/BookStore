package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class OrderBooksController {
    @FXML
    ComboBox<String> categories;
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
    void placeOrder(){
        if(utils.checkField(title)||utils.checkField(publication)||utils.checkField(authors)||utils.checkField(ISBN)
                ||utils.checkField(publisher)||utils.checkField(year)||utils.checkField(sellingPrice)||utils.checkField(quantity)){
            return;
        }
    }
}
