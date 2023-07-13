package com.quangpao.petemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "goods.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "product";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DESCRIPTION_COL = "description";
    private static final String PRICE_COL = "price";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+" ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + DESCRIPTION_COL + " TEXT, "
                + PRICE_COL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addNewProduct(String productName, String productDescription, String productPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, productName);
        values.put(DESCRIPTION_COL, productDescription);
        values.put(PRICE_COL, productPrice);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<ProductModal> readProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ProductModal> productModalArrayList = new ArrayList<>();

        Cursor cursorProducts = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if (cursorProducts.moveToFirst()) {
            do {
                productModalArrayList.add(new ProductModal(
                        cursorProducts.getInt(0),
                        cursorProducts.getString(1),
                        cursorProducts.getString(2),
                        cursorProducts.getString(3)
                ));
            } while (cursorProducts.moveToNext());
        }

        cursorProducts.close();
        db.close();

        return productModalArrayList;
    }

    public void updateProduct(String originalProductName, String productName, String productDescription, String productPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, productName);
        values.put(DESCRIPTION_COL, productDescription);
        values.put(PRICE_COL, productPrice);

        db.update(TABLE_NAME, values, "name= ?", new String[]{originalProductName});
        db.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}