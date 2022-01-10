package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckOutController {
    static Stage stage;
    Alert a = new Alert(Alert.AlertType.WARNING);
    @FXML
    TextField textField;
    @FXML
    void confirm(){
        CartAccess.checkOut(utils.getConnection());
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("done successfully");
        a.show();
        stage.close();

    }
}
