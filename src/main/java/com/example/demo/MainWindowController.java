package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    TableView<Book> cart;
    @FXML
    TableView resultOfSearch;
    @FXML
    TextField textField;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    TableColumn<Book,String> firstColumn;
    @FXML
    TableColumn<Book,String> secondColumn;
    @FXML
    TableColumn<Book,String> thirdColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.getItems().removeAll(comboBox.getItems());
        //fields of books
        comboBox.getItems().addAll("Option A", "Option B", "Option C");
        //default field;
        comboBox.getSelectionModel().select("Option B");
        //method to get string what is selected
        System.out.println(comboBox.getSelectionModel().getSelectedItem());
        ObservableList<Book>items = FXCollections.observableArrayList();




        firstColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
        cart.getItems().add(new Book("B1","a1","adf"));
        cart.getItems().add(new Book("B2","a2","category"));
        cart.setPlaceholder(new Label("no data"));
    }

}
