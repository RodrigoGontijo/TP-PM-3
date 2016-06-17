package com.tp.tppm3.Activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tp.tppm3.Firebase.SingletonFirebase;
import com.tp.tppm3.R;
import com.tp.tppm3.User.User;
import com.tp.tppm3.User.UserLocalStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private EditText lastName;
    private EditText firstName;
    private Button registerButton;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_register);

        setViews();
        setButtonClickListener();

    }

    @Override

    public void onBackPressed() {
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

        registerButton = (Button) findViewById(R.id.login_button);
    }

    private void setButtonClickListener() {
        registerButton.setOnClickListener(new View.OnClickListener() {
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

                    userLocalStore.setLoggedUser(true);
                    userLocalStore.storeUserData(newUser);

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
