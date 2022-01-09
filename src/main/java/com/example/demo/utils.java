package com.example.demo;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class utils {
  static   Connection connection;
  static String currentUsername;
  static Boolean isManager;
    static Timestamp transactionStartDate;

    public static Timestamp getTransactionStartDate() {
        return transactionStartDate;
    }

    public static void setTransactionStartDate(Timestamp transactionStartDate) {
        utils.transactionStartDate = transactionStartDate;
    }

    public static void setManager(Boolean manager){
      isManager=manager;
  }
  public static Boolean getManager(){
      return isManager;
  }
  public static void setUsername(String username){
      currentUsername=username;
  }
  public static String getCurrentUsername(){
      return currentUsername;
  }
    public static Connection getConnection(){
        return  connection;
    }
    public static void createConnection(){
        String myUrl = "jdbc:mysql://localhost/BookStore";
        try {
            connection = DriverManager.getConnection(myUrl,"ahmed","ahmedyasser248");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Boolean checkField(TextField textField){
        return textField.getText().isBlank()||textField.getText().isEmpty();
    }

}
