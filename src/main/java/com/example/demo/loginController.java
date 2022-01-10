package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    Alert a = new Alert(Alert.AlertType.WARNING);
    @FXML
    void login(){
        try {
            if(utils.checkField(email)||utils.checkField(password)){
                a.setContentText("complete your data");
                a.show();
                //warning.setText("complete data");
                return;
            }
            //BookStore is schema name
            //set your username and password
            Connection connection = utils.getConnection();
            String username = "'"+email.getText()+"'";
            String query = "SELECT * FROM CUSTOMER WHERE Username ="+username;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){

                String passwordUser = rs.getString("password");
                Boolean manager = rs.getBoolean("Manager");
                System.out.println(manager);
                utils.setManager(manager);
                if(!passwordUser.equals(password.getText())){
                    a.setContentText("wrong password");
                    a.show();
                    //warning.setText("wrong password");
                }else{
                    Pane singUpPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainwindow.fxml")));
                    utils.setUsername(email.getText());
                    root.getChildren().setAll(singUpPane);
                }

            }else{
                a.setContentText("no ysernam found");
                a.show();
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