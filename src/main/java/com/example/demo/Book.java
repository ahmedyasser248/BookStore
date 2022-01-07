package com.example.demo;

public class Book {
    int ISBN;
    String title;
    String author;
    String publisher;
    String publication_year;
    String sellingPrice;
    String category;
    public Book(String title, String author, String category){
        this.author=author;
        this.title=title;
        this.category=category;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
