package com.example.demo;

import javafx.fxml.FXML;

public class ReportsController {

    @FXML
    void getTopTenSellingBooks(){
        ReportAccess.getTopTenSellingBooks(utils.getConnection());
    }
    @FXML
    void getTopFiveCustomer(){
        ReportAccess.getTopFiveCustomers(utils.getConnection());
    }
    @FXML
    void getTotalSalesInPreciousMonth(){
        ReportAccess.getTotalSalesInPreviousMonth(utils.getConnection());
    }
}
