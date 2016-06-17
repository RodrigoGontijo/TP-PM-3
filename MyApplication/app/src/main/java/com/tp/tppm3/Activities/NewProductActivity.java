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
import com.tp.tppm3.Firebase.SingletonFirebase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NewProductActivity extends AppCompatActivity {

    private EditText name;
    private EditText price;
    private EditText url;
    private Button save;
    private Firebase firebase;
    DecimalFormat df = new DecimalFormat("#.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        firebase = SingletonFirebase.getConnection();


        setViews();
        setButtonListener();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ProductListActivity.class);
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

    private String getRandomId() {
        Random r = new Random();
        //generate a 16 digits number
        long result = r.nextLong();
        return Long.toString(result);

    }

    public void setButtonListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.getText().length() != 0 && name.getText().length() != 0){
                    try{
                        Map<String, Object> entry = new HashMap<>();
                        entry.put("id",getRandomId());
                        entry.put("Price",(Float.parseFloat(price.getText().toString())));
                        entry.put("Link", url.getText().toString());

                        firebase.child("Products").child(name.getText().toString()).setValue(entry);
                        onBackPressed();
                    }
                    catch (Exception ex){

                    }
                }
                // Deve avisar erro.
            }
        });

    }
}
