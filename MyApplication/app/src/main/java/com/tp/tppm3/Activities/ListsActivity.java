package com.tp.tppm3.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tp.tppm3.Firebase.SingletonFirebase;
import com.tp.tppm3.Product.Product;
import com.tp.tppm3.Product.ProductAdapter;
import com.tp.tppm3.Product.ProductList;
import com.tp.tppm3.Product.ProductListAdapter;
import com.tp.tppm3.R;
import com.tp.tppm3.User.User;

import java.util.ArrayList;
import java.util.List;

public class ListsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private User loggedUser;
    private List<ProductList> listProductList;
    private RecyclerView mRecyclerView;
    private ProductListAdapter adapter;
    private Firebase tppm3rep;
    public SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        tppm3rep = SingletonFirebase.getConnection();
        listProductList = new ArrayList<ProductList>();
        //checkLogin();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();


        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void readData() {

        final Firebase ref = tppm3rep.child("Lists");
        ref.addValueEventListener(new ValueEventListener() {

            // ISSO AQUI Ã‰ MUITO PERIGOSO. SE QUALQUER UMA ATUALIZAR O FIREBASE ISSO AQUI VAI SER CHAMADO.
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listProductList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {

                    String name = messageSnapshot.getKey();
                    String ownerId = messageSnapshot.child("OwnerId").getValue().toString();
                    int publicList = Integer.parseInt(messageSnapshot.child("Public").getValue().toString());
                    int count = Integer.parseInt(messageSnapshot.child("count").getValue().toString());

                    ProductList lista = new ProductList(name);

                    for (DataSnapshot ids : snapshot.child("Items").getChildren()) {
                        lista.add(ids.getValue().toString());
                    }


                    lista.setOwnerId(ownerId);
                    lista.setPublicList(publicList);
                    lista.setCount(count);

                    listProductList.add(lista);
                }


                adapter = new ProductListAdapter(ListsActivity.this, listProductList);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    @Override

    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.upload_product) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}