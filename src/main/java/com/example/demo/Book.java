package com.example.demo;

public class Book {
    int ISBN;
    String title;
    String author;
    String publisher;
    String publication_year;
    String sellingPrice;
    Category category;
    int inStock;
    int minQuantity;

    public Book(String title, String author, Category category){
        this.author=author;
        this.title=title;
        this.category=category;
    }

    public int getInStock() {
        return inStock;
    }

    public int getISBN() {
        return ISBN;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublication_year() {
        return publication_year;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setPublication_year(String publication_year) {
        this.publication_year = publication_year;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book{" +
            "title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", category='" + category + '\'' +
            '}';
    }
}
