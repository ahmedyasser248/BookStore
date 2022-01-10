package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class MainWindowController implements Initializable {
    @FXML
    TableView<Book> cart;
    @FXML
    TableView<Book> resultOfSearch;
    @FXML
    TextField textField;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    TableColumn<Book,String> firstColumnCart;
    @FXML
    TableColumn<Book,String> secondColumnCart;
    @FXML
    TableColumn<Book,Category> thirdColumnCart;
    @FXML
    TableColumn<Book,String> firstColumnSearch;
    @FXML
    TableColumn<Book,String> secondColumnSearch;
    @FXML
    TableColumn<Book,Category> thirdColumnSearch;
    @FXML
    Button additionalOperationsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utils.createConnection();
        comboBox.getItems().removeAll(comboBox.getItems());
        //fields of books
        comboBox.getItems().addAll("ISBN", "Title", "Publisher","Category","Author");
        //default field;
        comboBox.getSelectionModel().select("ISBN");
        //method to get string what is selected
        //System.out.println(comboBox.getSelectionModel().getSelectedItem());
        firstColumnCart.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumnCart.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumnCart.setCellValueFactory(new PropertyValueFactory<Book,Category>("category"));
        firstColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,Category>("category"));
      //  additionalOperationsButton.setVisible(utils.isManager);
        cart.setPlaceholder(new Label("no data"));

    }
    @FXML
    void addResultFromSearch(){
        resultOfSearch.getItems().removeAll();
        if(textField.getText().isEmpty()){
            return;
        }
        String filter= comboBox.getSelectionModel().getSelectedItem();
        ArrayList<Book> books = Queries.search(textField.getText(),utils.getConnection(),filter);
        for (int  i = 0 ; i < books.size();i++){
            if(books.get(i).inStock!=0)
                resultOfSearch.getItems().add(books.get(i));
        }


    }
    @FXML
    void deleteSelectedFromCart(){
        Book book = cart.getSelectionModel().getSelectedItem();
        cart.getItems().remove(cart.getSelectionModel().getSelectedItem());
        CartAccess.removeBookFromCart(book,utils.getConnection());
        if(book.inStock==1){
            resultOfSearch.getItems().add(book);
        }
    }
    @FXML
    void checkout(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("checkout.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("checkout");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void addSelectedToCart(){
        Book book = resultOfSearch.getSelectionModel().getSelectedItem();
        utils.setTransactionStartDate(Timestamp.valueOf(LocalDateTime.now()));
        CartAccess.addBookToCart(book,utils.getTransactionStartDate(),utils.getConnection());
        if(book.inStock==0){
            resultOfSearch.getItems().remove(book);
        }
        cart.getItems().add(book);
    }
    @FXML
    void openEditPersonalInformation(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editPersonalInformation.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("personal data");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void openAdditionOptions(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("additionaloperations.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("additional operations");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
