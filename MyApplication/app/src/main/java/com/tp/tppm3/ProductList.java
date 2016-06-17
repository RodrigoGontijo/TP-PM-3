package com.tp.tppm3;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<Product> list;
    private String name;
    private int id;

    public ProductList(String _name){
        this.list = new ArrayList<Product>();
        this.name = _name;
    }

    public void add(Product product){
        list.add(product);

    }
    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
