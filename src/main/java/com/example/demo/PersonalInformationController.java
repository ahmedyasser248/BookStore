package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInformationController implements Initializable {
    Alert a = new Alert(Alert.AlertType.WARNING);
    boolean manager;
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
    Label warning;

    @FXML
    void saveNewData(){

        //check if any fields got empty
        if(username.getText().isBlank()||password.getText().isBlank()||lastname.getText().isBlank()||firstName.getText().isBlank()||emailAddress.getText().isBlank()||phoneNumber.getText().isBlank()||shippingAddress.getText().isBlank()){
            a.setContentText("complete data");
            a.show();
            return;
        }
        if (!Validator.validateEmail(emailAddress.getText())){
            a.setContentText("wrong email format");
            a.show();
            return;
        }

        User user = new User(lastname.getText(),firstName.getText(),username.getText(),emailAddress.getText(),password.getText(),phoneNumber.getText(),shippingAddress.getText()
        ,manager);

        int result =UserAccess.updateUserInfo(user,utils.getConnection());
        utils.setUsername(username.getText());
        if(result==0){
            a.setContentText("an error occurred try again");
            a.show();
        }else{
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText(" done successfully");
            a.show();

        }

        User user1= UserAccess.readUserInfo(utils.getCurrentUsername(),utils.getConnection());
        username.setText(user1.userName);
        password.setText(user1.password);
        phoneNumber.setText(user1.phone);
        shippingAddress.setText(user1.shippingAddress);
        emailAddress.setText(user1.email);
        lastname.setText(user1.lastName);
        firstName.setText(user1.firstName);
        manager=user1.manager;

        return;
        //we may show a label or something.
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user= UserAccess.readUserInfo(utils.getCurrentUsername(),utils.getConnection());
        username.setText(user.userName);
        password.setText(user.password);
        phoneNumber.setText(user.phone);
        shippingAddress.setText(user.shippingAddress);
        emailAddress.setText(user.email);
        lastname.setText(user.lastName);
        firstName.setText(user.firstName);
        manager=user.manager;
    }
}
