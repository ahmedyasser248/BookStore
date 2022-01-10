package com.example.demo;

public class Validator {
    public static boolean validateISBN(String isbn){
        return isbn.matches("[0-9]{3}-[0-9]{2}-[0-9]{5}-[0-9]{2}-[0-9]");
    }
    public static boolean validateEmail(String email){
        return email.matches("^(.+)@(.+)$");
    }
 public static void main(String[] args) {
    String ahmed ="adad@" ;
     System.out.println(validateEmail(ahmed));
}

}
