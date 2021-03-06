package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class signUpController {
    @FXML
    Pane root;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    TextField lastname;
    @FXML
    TextField firstName;
    @FXML
    TextField emailAddress;
    @FXML
    TextField phoneNumber;
    @FXML
    TextField shippingAddress;
    @FXML
    ToggleGroup user;
    @FXML
    Label warning;
    Alert a = new Alert(Alert.AlertType.WARNING);
    @FXML
    void RegisterUser(){

        if(shippingAddress.getText().isEmpty()||username.getText().isEmpty()||password.getText().isEmpty()||lastname.getText().isEmpty()){
            a.setContentText("complete data");
            a.show();
            return;
        }
        if(firstName.getText().isEmpty()||emailAddress.getText().isEmpty()||phoneNumber.getText().isEmpty()){
            a.setContentText("complete data");
            a.show();
            return;
        }
        if(!Validator.validateEmail(emailAddress.getText())){
            a.setContentText("wrong format");
            a.show();
            return;
        }
        Boolean manager = user.getSelectedToggle().toString().contains("manager");
        User user1 = new User(firstName.getText(), lastname.getText(), username.getText(), emailAddress.getText(), password.getText(), phoneNumber.getText(), shippingAddress.getText(), manager);
       Boolean result =signup(user1);
        if(result){
            Pane singUpPane = null;
            try {
                utils.setManager(manager);
                utils.setUsername(username.getText());
                singUpPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainwindow.fxml")));
                root.getChildren().setAll(singUpPane);
            } catch (IOException e) {
                a.setContentText("try again");
                a.show();
                e.printStackTrace();
            }

        }else{
            a.setContentText("check data you entered");
            a.show();
        }
    }
    Boolean signup(User user){
        Connection connection=utils.getConnection();
        String query ="INSERT INTO CUSTOMER (Username,Password,Last_Name,First_Name,Email,Phone_Number,Shipping_Address,Manager) values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.userName);
            preparedStatement.setString(2,user.password);
            preparedStatement.setString(3,user.lastName);
            preparedStatement.setString(4,user.firstName);
            preparedStatement.setString(5,user.email);
            preparedStatement.setString(6,user.phone);
            preparedStatement.setString(7,user.shippingAddress);
            preparedStatement.setBoolean(8,user.manager);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
