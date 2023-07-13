package com.quangpao.petemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateProducts extends AppCompatActivity {

    private EditText productNameEdt, productDescriptionEdt, productPriceEdt;
    private Button updateProductBtn, deleteProductBtn;
    private DBHandler dbHandler;
    String productName, productDescription, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);

        productNameEdt = findViewById(R.id.idEdtProductName);
        productDescriptionEdt = findViewById(R.id.idEdtProductDescription);
        productPriceEdt = findViewById(R.id.idEdtProductPrice);
        updateProductBtn = findViewById(R.id.idBtnUpdateProduct);
        deleteProductBtn = findViewById(R.id.idBtnDeleteProduct);

        dbHandler = new DBHandler(UpdateProducts.this);

        productName = getIntent().getStringExtra("name");
        productDescription = getIntent().getStringExtra("description");
        productPrice = getIntent().getStringExtra("price");

        productNameEdt.setText(productName);
        productDescriptionEdt.setText(productDescription);
        productPriceEdt.setText(productPrice);

        updateProductBtn.setOnClickListener(v -> {
            dbHandler.updateProduct(productName,
                    productNameEdt.getText().toString(),
                    productDescriptionEdt.getText().toString(),
                    productPriceEdt.getText().toString());

            Toast.makeText(UpdateProducts.this, "Product Updated..", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateProducts.this, MainActivity.class);
            startActivity(i);
        });
    }
}