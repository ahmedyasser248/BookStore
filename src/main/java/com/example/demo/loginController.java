package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class loginController {
    @FXML
    Pane root;
    @FXML
    TextField email;
    @FXML
    TextField password;
    @FXML
    ToggleGroup user;

    @FXML
    void login(){
        try {
            //BookStore is schema name
            String myUrl = "jdbc:mysql://localhost/BookStore";
            //set your username and password
            Connection connection = DriverManager.getConnection(myUrl,"ahmed","ahmedyasser248");
            String query = "SELECT * FROM USERS";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("email");
                String pass = rs.getString("password");
                System.out.println(name+"  "+pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //tODO check if user exists
        if(user.getSelectedToggle().toString().contains("user")){
            //normal user
        }else{
            //manager
        }
    }
    @FXML
    void signUp() throws IOException {
        Pane singUpPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup.fxml")));
        root.getChildren().setAll(singUpPane);
    }

}