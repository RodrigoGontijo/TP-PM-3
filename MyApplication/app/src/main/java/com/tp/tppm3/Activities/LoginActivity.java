package com.tp.tppm3.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.*;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tp.tppm3.R;
import com.tp.tppm3.Firebase.SingletonFirebase;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.tp.tppm3.User.*;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button loginButton;
    private Button register;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_login);
        setViews();
        setButtonClickListener();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void setViews() {
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        loginButton = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);
    }

    private void setButtonClickListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mailText = email.getText().toString();
                final String passText = pass.getText().toString();
                Firebase ref = SingletonFirebase.getConnection().child("Users");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            if(messageSnapshot.child("Email").getValue().equals(mailText) && messageSnapshot.child("Password").getValue().equals(passText)){
                                userLocalStore.setLoggedUser(true);
                                User user = new User(messageSnapshot.getKey().toString(), passText, mailText, messageSnapshot.child("Id").getValue().toString());
                                userLocalStore.storeUserData(user);
                                callMain();
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                    });
                }


        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getRandomId() {
        Random r = new Random();
        //generate a 16 digits number
        long result = r.nextLong();
        return Long.toString(result);

    }


    private void callMain() {
        // return/add the userID somewhere
        Intent intent = new Intent(this, ListsActivity.class);
        startActivity(intent);

    }


    private void setUser(String email, String id, String name, String Password){

            Map<String, String> newUser = new HashMap<>();
            newUser.put("Id", id);
            newUser.put("Email", email);
            newUser.put("Password", Password);

            SingletonFirebase.getConnection().child("Users").child(name).setValue(newUser);


    }

}
