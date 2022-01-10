package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//TODO add label
public class PromoteUsersController {
    @FXML
    Label warning;
    @FXML
    TextField search;
    @FXML
    Label message;
    @FXML
    void promote(){
        if(utils.checkField(search)){
            return;
        }
        Queries.promoteUser(search.getText(),utils.getConnection());

    }
}
