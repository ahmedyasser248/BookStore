package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmOrderBooksController implements Initializable {
    @FXML
    TableView<BookOrders> books;
    @FXML
    TableColumn<BookOrders,String> firstColumn;
    @FXML
    TableColumn<BookOrders,String> secondColumn;
    @FXML
    TableColumn<BookOrders,String> thirdColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO query to get all books that should be ordered;


        //then add them to books
        firstColumn.setCellValueFactory(new PropertyValueFactory<BookOrders,String>("ISBN"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<BookOrders,String>("quantityOrdered"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<BookOrders,String>("orderDate"));

    }
    @FXML
    void confirmBook(){
        BookOrders order = books.getSelectionModel().getSelectedItem();
        //TODO update confirm in database
        //remove from table
        books.getItems().remove(order);
    }
    @FXML
    void deleteBook(){
        BookOrders order = books.getSelectionModel().getSelectedItem();
        //TODO remove from orders table
        //remove from table
        books.getItems().remove(order);
    }
}
