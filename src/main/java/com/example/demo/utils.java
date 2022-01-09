package com.example.demo;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class utils {
    public static Connection getConnection(){
        String myUrl = "jdbc:mysql://localhost/BookStore";
        //set your username and password
        Connection connection=null;
        try {
            connection = DriverManager.getConnection(myUrl,"ahmed","ahmedyasser248");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  connection;
    }
    public static Boolean checkField(TextField textField){
        return textField.getText().isBlank()||textField.getText().isEmpty();
    }
}
