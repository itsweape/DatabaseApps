package com.example.databaseapps;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private static DatabaseHelper mDatabaseHelpper;
    private static ArrayList<Product> products = new ArrayList<>();
    RecyclerView recyclerView;
    ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mDatabaseHelpper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.rv);

        //memanggil fungsi getAllProduct dari class DatabaseHelper
        products = mDatabaseHelpper.getAllProduct();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewData();


    }

    private void viewData(){
        mAdapter = new ProductAdapter(this, R.layout.item_product, products);
        recyclerView.setAdapter(mAdapter);
    }
}