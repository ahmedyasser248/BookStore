package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        ArrayList<BookOrders> bookOrdersArray = new ArrayList<>();
        try {
            Connection conn = utils.getConnection();
            String query = "select *"
                + "from book_order";
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

        //then add them to books
        firstColumn.setCellValueFactory(new PropertyValueFactory<BookOrders,String>("ISBN"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<BookOrders,String>("quantityOrdered"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<BookOrders,String>("orderDate"));

    }
    @FXML
    void confirmBook(){
        BookOrders order = books.getSelectionModel().getSelectedItem();
        // confirm the order in the DB
        Queries.confirmOrder(order.ISBN,utils.getConnection());
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
