package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categories.getItems().removeAll(categories.getItems());
        //fields of books
        categories.getItems().addAll("Science", "Art", "Religion","History","Geography");
        //default field;
        categories.getSelectionModel().select("Science");
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
            return;
        }
        //TODO query to add book
    }
}
