package com.tp.tppm3.Product;


import com.firebase.client.Firebase;

public class Product {
    private String name;
    private float price;


    public Product(String name, float price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }





}
