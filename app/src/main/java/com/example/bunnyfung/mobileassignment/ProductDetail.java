package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class ProductDetail extends Activity {
    SQLiteDatabase db;
    TextView tvProdNum,tvProdName,tvProdPrice,tvJobProblems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        tvProdNum = (TextView)findViewById(R.id.tvProdNum);
        tvProdName = (TextView)findViewById(R.id.tvProdName);
        tvProdPrice = (TextView)findViewById(R.id.tvProdPrice);
        tvJobProblems = (TextView)findViewById(R.id.tvJobProblems);
        Intent intent = getIntent();
        String prodNo = intent.getStringExtra("prodNo");
        Cursor cursor;
        Cursor cursor1 = null;
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("Select * from Product where prodNo='"+ prodNo+"'", null);
            String str = "";
            while (cursor.moveToNext()) {
                String prodNum = cursor.getString(cursor.getColumnIndex("prodNo"));
                tvProdNum.setText(prodNum);
                String prodName = cursor.getString(cursor.getColumnIndex("prodName"));
                tvProdName.setText(prodName);
                int prodPrice = cursor.getInt(cursor.getColumnIndex("prodPrice"));
                tvProdPrice.setText(""+prodPrice);
            }
            cursor = db.rawQuery("Select serialNo from Purchase where prodNo='"+ prodNo+"'", null);
            String purchase[] = new String [cursor.getCount()];
            while (cursor.moveToNext()){
                String serialNo = cursor.getString(cursor.getColumnIndex("serialNo"));
                cursor1 = db.rawQuery("Select jobProblem from ServiceJob where serialNo='"+ serialNo+"'", null);
                int counter = 1;
                while (cursor1.moveToNext()){
                    str += counter +". ";
                    String jobProblem = cursor1.getString(cursor1.getColumnIndex("jobProblem"));
                    str += jobProblem + "\n";
                    counter++;
                }
            }
            db.close();
            tvJobProblems.setText(str);

        }
            catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
