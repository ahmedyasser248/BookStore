package com.example.demo;

import java.sql.*;

public  class UserAccess {


    private static void storeUserInfo(User user, String userName, ResultSet rs){
        try {
            while(rs.next()) {
                user.firstName = rs.getString("First_Name");
                user.lastName = rs.getString("Last_Name");
                user.email = rs.getString("Email");
                user.userName=userName;
                user.password = rs.getString("Password");
                user.shippingAddress = rs.getString("Shipping_Address");
                user.phone = rs.getString("Phone_Number");
                user.manager = rs.getBoolean("Manager");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public static User readUserInfo(String userName, Connection connection){//TODO Do we need to commit on read
        User user=new User();
        try{
            String query="SELECT * FROM CUSTOMER WHERE Username=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,userName);
            ResultSet rs=preparedStatement.executeQuery();
            storeUserInfo(user,userName,rs);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }






    public static int updateUserInfo(User user,Connection connection){//TODO Handle the invalid data

        String query="UPDATE CUSTOMER SET First_Name="+"'"+ user.firstName+"'"+
                ",Last_Name="+"'"+ user.lastName+"'"+
                ",Email=" +"'"+ user.email+"'"+
                ",Username=" +"'"+ user.userName+"'"+
                ",Password=" +"'"+ user.password+"'"+
                ",Shipping_Address=" +"'"+ user.shippingAddress+"'"+
                ",Phone_Number="+ "'"+ user.phone+"'"+
                ",Manager="+user.manager;
        query +=" WHERE Username="+"'"+utils.getCurrentUsername()+"'"+";";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0;//Failure has happened because email is not unique or phone number isn't unique or  userName already exists
        }
        return 1;
    }
}