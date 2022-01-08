package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInformationController implements Initializable {
    @FXML
    Button saveButton;
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
    void saveNewData(){
        //check if any fields got empty
        if(username.getText().isBlank()||password.getText().isBlank()||lastname.getText().isBlank()||firstName.getText().isBlank()||emailAddress.getText().isBlank()||phoneNumber.getText().isBlank()||shippingAddress.getText().isBlank()){
            return;
        }
        //TODO query to update data

        return;
        //we may show a label or something.
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ///TODO query to get personal data an populate fields with it
        /*
        username.setText("");
        emailAddress.setText("");
        password.setText("");
        lastname.setText("");
        firstName.setText("");
        phoneNumber.setText("");
        shippingAddress.setText("");
        */

    }
}
