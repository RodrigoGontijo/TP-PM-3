package com.tp.tppm3.User;
import com.tp.tppm3.Product.ProductList;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<ProductList> listOfProductLists;
    private String name;

    public User(String _name){
        listOfProductLists = new ArrayList<ProductList>();
        this.name = _name;
    }

    public List<ProductList> getListOfProductLists() {
        return listOfProductLists;
    }

    public void setListOfProductLists(List<ProductList> listOfProductLists) {
        this.listOfProductLists = listOfProductLists;
    }


    public void addList(ProductList newList){
        listOfProductLists.add(newList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
