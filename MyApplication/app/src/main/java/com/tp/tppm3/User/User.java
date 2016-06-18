package com.tp.tppm3.User;
import com.tp.tppm3.List.ProductList;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static final String SP_NAME = "userDetails";
    private List<ProductList> listOfProductLists;
    public String name;
    private String password;
    public String email;
    private String ID;

    public User(String _name, String _password, String _email, String _id){
        listOfProductLists = new ArrayList<ProductList>();
        this.name = _name;
        this.password = _password;
        this.email = _email;
        this.ID = _id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getId(){return ID;}

    public String getPassword(){return password;}



}
