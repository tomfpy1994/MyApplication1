package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;


public class ComDetails extends Activity {
    SQLiteDatabase db;
    Cursor cursor=null;
    TextView tvComAddr = (TextView)findViewById(R.id.tvComAddr);
    WebView webView1 = (WebView)findViewById(R.id.webView1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_details);
        TextView tvComNo = (TextView)findViewById(R.id.tvComNo);
        TextView tvComName = (TextView)findViewById(R.id.tvComName);
        TextView tvComTel = (TextView)findViewById(R.id.tvComTel);


        Intent intent = getIntent();
        String comNo = intent.getStringExtra("comNo");

        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("Select * from Company where comNo='"+ comNo+"'", null);
            while (cursor.moveToNext()) {
                tvComNo.setText(comNo);
                String comName = cursor.getString(cursor.getColumnIndex("comName"));
                tvComName.setText(comName);
                String comTel = cursor.getString(cursor.getColumnIndex("comTel"));
                tvComTel.setText(comTel);
                String comAddr = cursor.getString(cursor.getColumnIndex("comAddr"));
                tvComAddr.setText(comAddr);
            }
            db.close();
        }
        catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        webView1.setWebViewClient(new WebViewClient());
        webView1.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_com_details, menu);
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
}
