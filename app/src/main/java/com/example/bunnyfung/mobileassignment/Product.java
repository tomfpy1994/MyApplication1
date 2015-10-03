package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Product extends Activity implements AdapterView.OnItemClickListener {
    ListView lvProduct;
    SQLiteDatabase db;
    String [] item;
    private ArrayList<ProdItem> data = new ArrayList<>();
    private ProdItemAdapter lvProdItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Cursor cursor;
        lvProduct = (ListView) findViewById(R.id.lvProduct);

        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("Select prodNo,prodName from Product", null);
            while (cursor.moveToNext()) {
                String prodNo = "" + cursor.getString(cursor.getColumnIndex("prodNo"));
                String prodName = "" + cursor.getString(cursor.getColumnIndex("prodName"));
                ProdItem entry = new ProdItem(prodNo, prodName);
                data.add(entry);
            }
            lvProdItemAdapter = new ProdItemAdapter(this, R.layout.signle_proditem, data);
            lvProduct.setAdapter(lvProdItemAdapter);
            db.close();
        }
        catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        lvProduct.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = data.get(position).getProdNo();
        Intent intent = new Intent(this, ProductDetail.class);
        intent.putExtra("prodNo", selectedItem);
        startActivity(intent);
    }
}
