package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CheckOutController {
    @FXML
    TextField textField;
    @FXML
    void confirm(){
        CartAccess.checkOut(utils.getConnection());
    }
}
