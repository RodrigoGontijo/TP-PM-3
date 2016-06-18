package com.tp.tppm3.List;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<String> productsIds;
    private String name;
    private String ownerId;
    private boolean publicList;
    private int count;

    public ProductList(String _name){
        this.productsIds = new ArrayList<String>();
        this.name = _name;
    }

    public void add(String idProduct){
        productsIds.add(idProduct);

    }
    public List<String> getList() {
        return productsIds;
    }

    public void setList(List<String> list) {
        this.productsIds = list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId(){ return ownerId; }

    public void setOwnerId(String value){ ownerId=value; }

    public int getCount(){return count;}

    public void setCount(int value){count = value;}

    public boolean getPublicList(){return publicList;}

    public void setPublicList(boolean value){publicList = value;}

}
