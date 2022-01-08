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
        firstColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
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
