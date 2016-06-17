package com.tp.tppm3.User;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Caio Godoy on 17/06/2016.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("id", user.getId());
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.getPassword());
        spEditor.commit();
    }

    public User getLoggedUser(){
        String name = userLocalDatabase.getString("name", "");
        String id = userLocalDatabase.getString("id", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");

        return new User(name, password, email, id);
    }

    public void setLoggedUser(boolean logged){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", logged);
        spEditor.commit();
    }

    public boolean isUserLogged(){
        if(userLocalDatabase.getBoolean("loggedIn", false)){
            return true;
        }
        else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
