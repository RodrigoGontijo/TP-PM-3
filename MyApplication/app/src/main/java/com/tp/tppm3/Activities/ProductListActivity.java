package com.tp.tppm3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tp.tppm3.Firebase.SingletonFirebase;
import com.tp.tppm3.Product.Product;
import com.tp.tppm3.Product.ProductAdapter;
import com.tp.tppm3.R;
import com.tp.tppm3.User.UserLocalStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList;
    private RecyclerView mRecyclerView;
    private ProductAdapter adapter;
    private Firebase tppm3rep;
    UserLocalStore localStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        localStore = new UserLocalStore(this);

        tppm3rep = SingletonFirebase.getConnection();
        productList = new ArrayList<Product>();

        Bundle bundle = getIntent().getExtras();
        if (getIntent().getStringExtra("List_ID") != null) {
            getListProducts(bundle.getString("List_ID"));
        }else{
            readAllProducts();
        }



        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_products);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void readAllProducts() {

        final Firebase ref = tppm3rep.child("Products");
        ref.addValueEventListener(new ValueEventListener() {

            // ISSO AQUI É MUITO PERIGOSO. SE QUALQUER UMA ATUALIZAR O FIREBASE ISSO AQUI VAI SER CHAMADO.
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {

                    String name =  messageSnapshot.getKey();
                    Float price  = Float.parseFloat(messageSnapshot.child("Price").getValue().toString());

                    if(messageSnapshot.hasChild("Link") && messageSnapshot.hasChild("id")){
                        String id  = messageSnapshot.child("id").getValue().toString();
                        String link  = messageSnapshot.child("Link").getValue().toString();
                    }
                    Product item = new Product(name, price);
                    productList.add(item);
                }


                adapter = new ProductAdapter(ProductListActivity.this, productList);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    private void getListProducts(final String name) {

        final Firebase listRef = tppm3rep;
        listRef.addValueEventListener(new ValueEventListener() {

            // ISSO AQUI É MUITO PERIGOSO. SE QUALQUER UMA ATUALIZAR O FIREBASE ISSO AQUI VAI SER CHAMADO.
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot listSnapshot : snapshot.child("Lists").getChildren())
                {
                    if(listSnapshot.getKey().equals(name))
                    {
                        String listName =  listSnapshot.getKey();
                        Boolean isPublic = (Boolean) listSnapshot.child("Public").getValue();
                        ArrayList<String> items = new ArrayList<String>();
                        for(DataSnapshot productsId : listSnapshot.child("Items").getChildren()){
                            items.add(productsId.getValue().toString());
                        }
                        for (DataSnapshot productsSnapshots : snapshot.child("Products").getChildren())
                        {
                            if(!items.isEmpty()){
                                for(String value : items)
                                {
                                    if(productsSnapshots.child("id").getValue().equals(value))
                                    {
                                        Float price  = Float.parseFloat(productsSnapshots.child("Price").getValue().toString());

                                        if(listSnapshot.hasChild("id")){
                                            String id  = listSnapshot.child("id").getValue().toString();
                                        }

                                        Product item = new Product(productsSnapshots.getKey(), price);
                                        productList.add(item);
                                    }
                                }
                            }
                        }
                    }
                }


                adapter = new ProductAdapter(ProductListActivity.this, productList);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_product) {
            Intent intent = new Intent(this, NewProductActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
