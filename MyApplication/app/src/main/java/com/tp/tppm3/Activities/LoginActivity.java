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
    private EditText lastName;
    private EditText firstName;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setViews();
        setButtonClickListener();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void setViews() {
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        loginButton = (Button) findViewById(R.id.login_button);
    }

    private void setButtonClickListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email != null && firstName.getText()!=null && lastName.getText()!=null)
                {
                    String mailText = email.getText().toString();
                    String passText = pass.getText().toString();
                    String FullName = firstName.getText().toString().concat(" ").concat(lastName.getText().toString());
                    String id = getRandomId();

                    User newUser = new User(FullName, passText, mailText, id);

                    setUser(mailText, id, FullName, passText);
                    callMain(newUser);
                }
            }
        });
    }

    private String getRandomId() {
        Random r = new Random();
        //generate a 16 digits number
        long result = r.nextLong();
        return Long.toString(result);

    }


    private void callMain(User newUser) {
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
