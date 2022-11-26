package com.example.databaseapps;

public class Product {
    private int id;
    private String name;
    private int price;

    //constructor tanpa id
    public Product(String product, int price) {
        this.name = product;
        this.price = price;
    }

    //constructor dengan id
    public Product(int id, String product, int price) {
        this.id = id;
        this.name = product;
        this.price = price;
    }

    //getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
