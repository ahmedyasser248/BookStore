package com.example.demo;

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
    double totalPrice =0;
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
    TableColumn<Book,Double>fourthColumnCart;
    @FXML
    TableColumn<Book,String> firstColumnSearch;
    @FXML
    TableColumn<Book,String> secondColumnSearch;
    @FXML
    TableColumn<Book,Category> thirdColumnSearch;
    @FXML
    TableColumn<Book,Double> fourthColumnSearch;
    @FXML
    Button additionalOperationsButton;
    @FXML
    Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        fourthColumnCart.setCellValueFactory(new PropertyValueFactory<Book,Double>("sellingPrice"));
        firstColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,Category>("category"));
        fourthColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,Double>("sellingPrice"));
        additionalOperationsButton.setVisible(utils.isManager);
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
            if(books.get(i).inStock!=0){
                resultOfSearch.getItems().add(books.get(i));
            }
        }


    }
    @FXML
    void deleteSelectedFromCart(){
        if(cart.getSelectionModel().getSelectedItem()==null){
            return;
        }
        Book book = cart.getSelectionModel().getSelectedItem();
        cart.getItems().remove(cart.getSelectionModel().getSelectedItem());
        CartAccess.removeBookFromCart(book,utils.getConnection());
        totalPrice -=book.sellingPrice;
        label.setText("total price : "+ totalPrice);
        if(book.inStock==1){
            resultOfSearch.getItems().add(book);
        }
    }
    @FXML
    void checkout(){
        try{
            cart.getItems().removeAll();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("checkout.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("checkout");
            stage.setScene(scene);
            CheckOutController.stage=stage;
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void addSelectedToCart(){
        if(resultOfSearch.getSelectionModel().getSelectedItem()==null){
            return;
        }
        Book book = resultOfSearch.getSelectionModel().getSelectedItem();
        utils.setTransactionStartDate(Timestamp.valueOf(LocalDateTime.now()));
        CartAccess.addBookToCart(book,utils.getTransactionStartDate(),utils.getConnection());
        if(book.inStock==0){
            resultOfSearch.getItems().remove(book);
        }
        cart.getItems().add(book);
        totalPrice +=book.sellingPrice;
        label.setText("total price : "+ totalPrice);
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
