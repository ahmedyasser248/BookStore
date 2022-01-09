package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class signUpController {
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

    @FXML
    void RegisterUser(){
        if(shippingAddress.getText().isEmpty()||username.getText().isEmpty()||password.getText().isEmpty()||lastname.getText().isEmpty()){
            warning.setText("complete data");
            return;
        }
        if(firstName.getText().isEmpty()||emailAddress.getText().isEmpty()||phoneNumber.getText().isEmpty()){
            warning.setText("complete data");
            return;
        }
        Boolean manager = user.getSelectedToggle().toString().equals("manager");

    }
    Boolean signup(User user){
        //String query ="INSERT INTO CUSTOMER"
        return true;
    }

}
