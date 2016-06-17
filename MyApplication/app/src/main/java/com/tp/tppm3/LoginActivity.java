package com.tp.tppm3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.tp.tppm3.Activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private EditText lastName;
    private EditText firstName;
    private Button loginButton;
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        setViews();
        facebookCallback();
        facebookLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        setButtonClickListener();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }


    private void facebookCallback() {
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Tag: ", String.valueOf(loginResult.getRecentlyGrantedPermissions()));
                getUserInformation(loginResult);

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Tag: ", String.valueOf(exception.toString()));
                // App code
            }
        });
    }




    private void getUserInformation(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String name = object.getString("name");

                            if (email != null && id !=null && name!=null) {
                                setUser(email,id, name);
                                callMain();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }





    private void setViews() {
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        loginButton = (Button) findViewById(R.id.login_button);
        facebookLoginButton = (LoginButton) findViewById(R.id.login_button_facebook);
    }

    private void setButtonClickListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email != null && firstName.getText()!=null && lastName.getText()!=null){
                    getRandomId();
                    setUser(email.getText().toString(), getRandomId(), firstName.getText().toString().concat(" ").concat(lastName.getText().toString()));
                    callMain();
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


    private void callMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    private void setUser(String email, String id, String name){
        SingletonFirebase.getConnection().child("Users").child(name).setValue(id);
        //SingletonFirebase.getConnection().child("Users").child(name).child(email);
    }

}
