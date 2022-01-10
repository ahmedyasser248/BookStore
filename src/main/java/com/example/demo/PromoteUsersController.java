package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.sf.jasperreports.data.qe.QueryExecuterDataAdapter;

//TODO add label
public class PromoteUsersController {
    Alert a = new Alert(Alert.AlertType.WARNING);

    @FXML
    Label warning;
    @FXML
    TextField search;
    @FXML
    Label message;
    @FXML
    void promote(){
        if(utils.checkField(search)){
            a.setContentText("search box is empty");
            a.show();
            return;
        }
       boolean found = Queries.userFound(search.getText(),utils.getConnection());
        if(found){
            Queries.promoteUser(search.getText(),utils.getConnection());
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("user promoted");
            a.show();
        }else{
            a.setContentText("user not found");
            a.show();
            return;
        }
    }
}
