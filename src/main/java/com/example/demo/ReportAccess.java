package com.example.demo;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportAccess {

    public static void getTotalSalesInPreviousMonth(Connection connection){
        try{
            String path="TotalSalesInPreviousMonth.jrxml";
            JasperDesign jasperDesign= JRXmlLoader.load(path);
            String query= "SELECT *" +
                    "\tFROM BOOK AS B,Book_Sale AS BS\n" +
                    "    WHERE B.ISBN=BS.ISBN AND BS.Sale_Date >= CURDATE() - INTERVAL 1 MONTH AND \n" +
                    "    BS.Sale_Date < CURDATE() + INTERVAL 1 DAY;";

            JRDesignQuery newQuery=new JRDesignQuery();
            newQuery.setText(query);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jp= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jp,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void getTopTenSellingBooks(Connection connection){
        try{
            String path="TopTenSellingBooks.jrxml";
            JasperDesign jasperDesign= JRXmlLoader.load(path);
            String query= "SELECT * \n" +
                    "FROM BOOK AS B , Book_Sale AS BS\n" +
                    "WHERE B.ISBN=BS.ISBN \n" +
                    "AND B.ISBN IN\n" +
                    "      (SELECT B.ISBN\n" +
                    "FROM BOOK AS B,Book_Sale AS BS\n" +
                    "WHERE B.ISBN=BS.ISBN AND BS.Sale_Date >= CURDATE() - INTERVAL 3 MONTH AND \n" +
                    "BS.Sale_Date < (CURDATE() + INTERVAL 1 DAY)\n" +
                    "GROUP BY B.ISBN\n" +
                    "ORDER BY SUM(Sold_Quantity) DESC)";

            JRDesignQuery newQuery=new JRDesignQuery();
            newQuery.setText(query);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jp= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jp,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }


    public static void getTopFiveCustomers(Connection connection){
        try{
            String path="TopFiveCustomers.jrxml";
            JasperDesign jasperDesign= JRXmlLoader.load(path);
            String query= "SELECT * \n" +
                    "FROM Book_Sale AS BS,CUSTOMER AS C\n" +
                    "WHERE BS.Customer_Username=C.Username \n" +
                    "AND C.Username IN\n" +
                    "\t(SELECT Customer_Username\n" +
                    "\tFROM Book_Sale AS BS,CUSTOMER AS C\n" +
                    "\tWHERE BS.Customer_Username = C.Username \n" +
                    "\tGROUP BY Customer_Username\n" +
                    "\tORDER BY SUM(BS.Sold_Quantity) DESC)";

            JRDesignQuery newQuery=new JRDesignQuery();
            newQuery.setText(query);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jp= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jp,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }


}