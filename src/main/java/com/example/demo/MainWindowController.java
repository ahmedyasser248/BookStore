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
    TableColumn<Book,String> thirdColumnCart;
    @FXML
    TableColumn<Book,String> firstColumnSearch;
    @FXML
    TableColumn<Book,String> secondColumnSearch;
    @FXML
    TableColumn<Book,String> thirdColumnSearch;
    @FXML
    Button additionalOperationsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBox.getItems().removeAll(comboBox.getItems());
        //fields of books
        comboBox.getItems().addAll("ISBN", "Title", "Publisher_Name","Publication_Year","Selling_Price","Category","Min_Quantity","In_Stock");
        //default field;
        comboBox.getSelectionModel().select("ISBN");
        //method to get string what is selected
        //System.out.println(comboBox.getSelectionModel().getSelectedItem());
        firstColumnCart.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumnCart.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumnCart.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
        firstColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumnSearch.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
        additionalOperationsButton.setVisible(utils.isManager);
        cart.getItems().add(new Book("B1","a1","adf"));
        cart.getItems().add(new Book("B2","a2","category"));
        cart.setPlaceholder(new Label("no data"));

    }
    @FXML
    void addResultFromSearch(){
        if(textField.getText().isEmpty()){
            return;
        }
        //TODO query to get data from Search



    }
    @FXML
    void deleteSelectedFromCart(){
        cart.getItems().remove(cart.getSelectionModel().getSelectedItem());
    }
    @FXML
    void addSelectedToCart(){

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
