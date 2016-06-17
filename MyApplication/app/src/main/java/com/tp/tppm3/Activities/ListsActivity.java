package com.tp.tppm3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import com.tp.tppm3.Product.ProductList;
import com.tp.tppm3.Product.ProductListAdapter;
import com.tp.tppm3.R;
import com.tp.tppm3.User.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

public class ListsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<ProductList> listProductList;
    private RecyclerView mRecyclerView;
    private ProductListAdapter adapter;
    private Firebase tppm3rep;
    UserLocalStore localStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        localStore = new UserLocalStore(this);
        tppm3rep = SingletonFirebase.getConnection();
        listProductList = new ArrayList<ProductList>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListsActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        checkLogin();
        readData();
    }

    private void readData() {

        final Firebase ref = tppm3rep.child("Lists");
        ref.addValueEventListener(new ValueEventListener() {

            // ISSO AQUI Ã‰ MUITO PERIGOSO. SE QUALQUER UMA ATUALIZAR O FIREBASE ISSO AQUI VAI SER CHAMADO.
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listProductList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    if( messageSnapshot.child("Public").getValue() == true || messageSnapshot.child("OwnerId").getValue().equals(localStore.getLoggedUser().getId())){
                        String name = messageSnapshot.getKey();
                        String ownerId = messageSnapshot.child("OwnerId").getValue().toString();
                        boolean publicList = (boolean)messageSnapshot.child("Public").getValue();
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
                }


                adapter = new ProductListAdapter(ListsActivity.this, listProductList);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    private void checkLogin() {

        if (!localStore.isUserLogged()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
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

        if(id == R.id.upload_product) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.upload_list) {
            Intent intent = new Intent(this, ListsActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.logout ){
            localStore.clearUserData();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}