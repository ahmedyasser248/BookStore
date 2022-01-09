package com.example.demo;

import java.sql.Timestamp;
import java.util.Date;

public class BookOrders {
    String ISBN;
    String quantityOrdered;
    Timestamp orderDate;

    BookOrders(String ISBN, String quantityOrdered, Timestamp date){
        this.ISBN=ISBN;
        this.quantityOrdered=quantityOrdered;
        this.orderDate=date;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setQuantityOrdered(String quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getQuantityOrdered() {
        return quantityOrdered;
    }

    public String getOrderDate() {
        return orderDate.toString();
    }
}
