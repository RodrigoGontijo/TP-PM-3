package com.tp.tppm3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.tp.tppm3.R;

public class NewProductActivity extends AppCompatActivity {

    private EditText name;
    private EditText price;
    private EditText url;
    private Button save;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


<<<<<<< HEAD:MyApplication/app/src/main/java/com/tp/tppm3/Activities/NewProductActivity.java
        firebase = new Firebase("https://tppm3.firebaseio.com//");
        firebase.child("Arroz").child("Price").setValue("2.45");
        firebase.child("Arroz").child("Link").setValue("http://perdendobarriga.com.br/wp-content/uploads/2016/04/arroz_branco.png");
=======
        firebase = SingletonFirebase.getConnection();
//        firebase.child("Arroz").child("Price").setValue("2.45");
//        firebase.child("Arroz").child("Link").setValue("http://perdendobarriga.com.br/wp-content/uploads/2016/04/arroz_branco.png");
>>>>>>> master:MyApplication/app/src/main/java/com/tp/tppm3/NewProductActivity.java

        setViews();
        setButtonListener();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setViews() {
        name = (EditText) findViewById(R.id.product_name);
        price = (EditText) findViewById(R.id.product_price);
        url = (EditText) findViewById(R.id.product_image_url);
        save = (Button) findViewById(R.id.save_button);
    }

    public void setButtonListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.child(name.getText().toString()).child("Price").setValue(price.getText().toString());
                firebase.child(name.getText().toString()).child("Link").setValue(url.getText().toString());
            }
        });

    }
}
