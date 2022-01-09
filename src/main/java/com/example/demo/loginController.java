package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    Pane root;
    @FXML
    TextField email;
    @FXML
    TextField password;
    @FXML
    ToggleGroup user;
    @FXML
    Label warning;

    @FXML
    void login(){
        try {
            //BookStore is schema name
            String myUrl = "jdbc:mysql://localhost/BookStore";
            //set your username and password
            Connection connection = utils.getConnection();
            String username = "'"+email.getText()+"'";
            String query = "SELECT * FROM USERS WHERE username ="+username;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                String passworduser = rs.getString("password");
                if(!passworduser.equals(password.getText())){
                    warning.setText("wrong password");
                }
                Pane singUpPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainwindow.fxml")));
                root.getChildren().setAll(singUpPane);
            }else{
                warning.setText("no username found");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void signUp() throws IOException {
        Pane singUpPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup.fxml")));
        root.getChildren().setAll(singUpPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utils.createConnection();
    }
}