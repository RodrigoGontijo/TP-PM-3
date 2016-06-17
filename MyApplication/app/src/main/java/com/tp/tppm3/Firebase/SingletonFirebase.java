package com.tp.tppm3.Firebase;


import com.firebase.client.Firebase;

public class SingletonFirebase {

    private static Firebase connection;

    public static Firebase getConnection() {
        if (connection == null) {
            new SingletonFirebase();
        }
        return connection;
    }


    private SingletonFirebase() {
        connection = new Firebase("https://tppm3.firebaseio.com//");
    }



}
