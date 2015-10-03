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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Company extends Activity {
    SQLiteDatabase db;
    private ArrayList<ComItem> data = new ArrayList<>();
    private ComItemAdapter lvItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Cursor cursor;
        TextView tvMsg = (TextView)findViewById(R.id.tvMsg);
        ListView lvList = (ListView) findViewById(R.id.lvList);

        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("Select * from Company", null);
            while (cursor.moveToNext()) {
                String comNo = cursor.getString(cursor.getColumnIndex("comNo"));
                String comName = cursor.getString(cursor.getColumnIndex("comName"));
                String comTel = cursor.getString(cursor.getColumnIndex("comTel"));
                String comAddr = cursor.getString(cursor.getColumnIndex("comAddr"));
                ComItem entry = new ComItem(comNo, comName, comTel, comAddr);
                data.add(entry);
            }
            lvItemAdapter = new ComItemAdapter(this, R.layout.com_item, data);
            lvList.setAdapter(lvItemAdapter);
        }
        catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            tvMsg.setText(e.getMessage());
        }
    }
}
