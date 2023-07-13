package com.quangpao.petemplate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quangpao.petemplate.broadcast.MyBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    MyBroadcastReceiver myBroadcastReceiver1 = new MyBroadcastReceiver();
    private EditText productNameEdt, productDescriptionEdt, productPriceEdt;
    private Button addProductBtn, readProductsBtn;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameEdt = findViewById(R.id.idEdtProductName);
        productDescriptionEdt = findViewById(R.id.idEdtProductDescription);
        productPriceEdt = findViewById(R.id.idEdtProductPrice);
        addProductBtn = findViewById(R.id.idBtnAddProduct);
        readProductsBtn = findViewById(R.id.idBtnReadProducts);

        dbHandler = new DBHandler(MainActivity.this);

        addProductBtn.setOnClickListener(v -> {
            String productName = productNameEdt.getText().toString();
            String productDescription = productDescriptionEdt.getText().toString();
            String productPrice = productPriceEdt.getText().toString();

            if (productName.isEmpty() && productDescription.isEmpty() && productPrice.isEmpty()) {
                return;
            }
            dbHandler.addNewProduct(productName, productDescription, productPrice);
            Toast.makeText(MainActivity.this, "Product has been added", Toast.LENGTH_SHORT).show();
            productNameEdt.setText("");
            productDescriptionEdt.setText("");
            productPriceEdt.setText("");
        });

        readProductsBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ViewProducts.class);
            startActivity(i);
        });
    }

    //Options Menu
    //Create an Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    //Click an item in Options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.message)
            Toast.makeText(getApplicationContext(),"You clicked Message menu",Toast.LENGTH_SHORT).show();
        else if (item.getItemId() == R.id.picture)
            Toast.makeText(getApplicationContext(),"You clicked Picture menu",Toast.LENGTH_SHORT).show();
        else if (item.getItemId() == R.id.mode)
            Toast.makeText(getApplicationContext(),"You clicked Mode menu",Toast.LENGTH_SHORT).show();
        if (item.getItemId() == R.id.option_favorites1)
            Toast.makeText(getApplicationContext(),"You clicked Music menu",Toast.LENGTH_SHORT).show();
        else if (item.getItemId() == R.id.option_favorites2)
            Toast.makeText(getApplicationContext(),"You clicked Book menu",Toast.LENGTH_SHORT).show();
        else if (item.getItemId() == R.id.about)
            Toast.makeText(getApplicationContext(),"You clicked About menu",Toast.LENGTH_SHORT).show();
        else if (item.getItemId() == R.id.exit)
            finish();

        return (super.onOptionsItemSelected(item));
    }

    //Broadcast Receiver
    @Override
    protected void onStart() {
        super.onStart();
        //Change connectivity to wifi or mobile data
        IntentFilter filter = new
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver, filter);

        //Change airplane mode
        IntentFilter filter1 = new
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(myBroadcastReceiver1, filter1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);

        //Change airplane mode
        unregisterReceiver(myBroadcastReceiver1);
    }
}