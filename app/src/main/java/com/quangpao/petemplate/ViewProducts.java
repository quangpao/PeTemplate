package com.quangpao.petemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewProducts extends AppCompatActivity {

    private ArrayList<ProductModal> productModalArrayList;
    private DBHandler dbHandler;
    private ProductRVAdapter productRVAdapter;
    private RecyclerView productsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        productModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewProducts.this);
        productModalArrayList = dbHandler.readProducts();
        productRVAdapter = new ProductRVAdapter(productModalArrayList, ViewProducts.this);

        productsRV = findViewById(R.id.idRVProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewProducts.this, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(linearLayoutManager);
        productsRV.setAdapter(productRVAdapter);
    }
}