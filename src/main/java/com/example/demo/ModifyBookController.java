package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;


public class ModifyBookController implements Initializable {
    String isbn;
    String authors;
    @FXML
    Label warning;
    @FXML
    TextField searchBox;

    @FXML
    ComboBox<Category> categories;
    @FXML
    TextField title;
    @FXML
    TextField inStockTf;
    @FXML
    TextField author;
    @FXML
    TextField publisher;

    @FXML
    TextField year;
    @FXML
    TextField sellingPrice;
    @FXML
    TextField quantity;
    @FXML
    void search(){
        if(utils.checkField(searchBox)){
            warning.setText("search is empty");
            return;

        }
        if(!Validator.validateISBN(searchBox.getText())){
            warning.setText("wrong ISBN format");
            return;
        }

        utils.createConnection();
        String query = "select * "
                + "from BOOK as b , AUTHOR as a "
                + "WHERE b.ISBN="+"?" + " And b.ISBN = a.ISBN";
        Connection connection = utils.getConnection();
        PreparedStatement getBooks = null;
        try {
            getBooks = connection.prepareStatement(query);
            getBooks.setString(1,searchBox.getText());
            ResultSet result = getBooks.executeQuery();

            if(!result.next()){
                //not found
                System.out.println("not found");
            }else{
               isbn = result.getString("ISBN");
                authors = result.getString("Author_Name");
                String yearx = result.getString("Publication_Year");
                yearx=yearx.substring(0,4);
                System.out.println(yearx);
                String titlex = result.getString("Title");
                String publisher_Name = result.getString("Publisher_Name");
                Category category= Category.valueOf(result.getString("Category"));
                int minQuantity = result.getInt("Min_Quantity");
                int In_Stock = result.getInt("In_Stock");
                double sellingPriced=result.getDouble("Selling_Price");
                System.out.println("authors : " +authors);
                while (result.next()){
                    authors+=","+result.getString("Author_Name");
                }
                System.out.println("authors : " +authors);
                title.setText(titlex);
                year.setText(yearx);
                publisher.setText(publisher_Name);
                author.setText(authors);
                sellingPrice.setText(sellingPriced+"");
                quantity.setText(minQuantity+"");
                inStockTf.setText(In_Stock+"");
                categories.getSelectionModel().select(Category.Science);



            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error occurred");
        }



    }
    @FXML
    void modifyBook()  {
        if(utils.checkField(publisher)||utils.checkField(sellingPrice)||utils.checkField(year)||utils.checkField(quantity)||utils.checkField(inStockTf)||utils.checkField(title)||utils.checkField(author)||
                utils.checkField(sellingPrice)){
            warning.setText("complete your data");
            return;

        }

            if(!updateData()){
                warning.setText("check your modified data");
                System.out.println("error occurred");
            }else{
                try {
                    utils.getConnection().commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        utils.getConnection().rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }



    }
    Boolean updateData()  {

        Connection connection = utils.getConnection();
        try{
            String query="UPDATE BOOK set Title="+"'"+title.getText()+"', "+"Publisher_Name="+"'"+publisher.getText()+"', "+"Publication_Year="+year.getText()+
                    " ,Selling_Price="+sellingPrice.getText()+", "+
                    "Category="+"'"+categories.getSelectionModel().getSelectedItem()+"', "+"Min_Quantity="+quantity.getText()
                    +" , "+"In_Stock = "+inStockTf.getText()+" WHERE ISBN = '"+isbn+"'";
            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            //check for authors
            String newAuthors[] = author.getText().split(",");
            System.out.println(author.getText());
            String oldAuthors[]=authors.split(",");
            System.out.println(authors);

            ArrayList<String>currentAuthors = new ArrayList<>();
            //get different users
            HashSet<String> set1 = new HashSet<>();
            HashSet<String>set2 = new HashSet<>();
            set1.addAll(Arrays.asList(oldAuthors));
            set2.addAll(Arrays.asList(newAuthors));
            for (int  i = 0 ; i < oldAuthors.length;i++){
                if(!set2.contains(oldAuthors[i])){
                    String deleteQuery="DELETE FROM AUTHOR WHERE Author_Name='"+oldAuthors[i]+"'";
                    PreparedStatement preparedStatement1= connection.prepareStatement(deleteQuery);
                    preparedStatement1.executeUpdate();
                }
            }
            for(int  i = 0 ; i < newAuthors.length;i++){
                if(!set1.contains(newAuthors[i])){
                    String insertQuery="INSERT INTO AUTHOR(ISBN,Author_Name)values(?,?)";
                    PreparedStatement preparedStatement1=connection.prepareStatement(insertQuery);
                    preparedStatement1.setString(1,isbn);
                    preparedStatement1.setString(2,newAuthors[i]);
                    preparedStatement1.execute();
                }
            }
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return  false;
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categories.getItems().removeAll(categories.getItems());
        categories.getItems().addAll(Category.Religion,Category.History,Category.Art,Category.Geography,Category.Science);
    }
}
