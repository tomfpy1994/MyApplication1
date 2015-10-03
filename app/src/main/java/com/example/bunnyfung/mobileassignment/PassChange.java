package com.example.bunnyfung.mobileassignment;

import android.app.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PassChange extends Activity implements View.OnClickListener {
    Button btnChange;
    EditText etNewpsd1, etNewpsd2;
    TextView tvMsg;
    SQLiteDatabase db;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passchange);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        btnChange = (Button) findViewById(R.id.btnChange);
        etNewpsd1 = (EditText) findViewById(R.id.etNewpsd1);
        etNewpsd2 = (EditText) findViewById(R.id.etNewpsd2);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnChange.setOnClickListener(this);

        // Set up the drawer.
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void onClick(View v) {
        String newpsd1 = etNewpsd1.getText().toString();
        String newpsd2 = etNewpsd2.getText().toString();
        if (v.getId() == R.id.btnChange) {
            if (newpsd1.equals(newpsd2)) {
                setNewpsd(newpsd1);
            } else {
                tvMsg.setText("Password NOT mach!");
            }
        }
    }

    public void setNewpsd(String newpsd) {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READWRITE);
            String sql = "UPDATE Technician SET staffPswd='" + newpsd + "' WHERE staffLogin='" +
                    username + "';";
            db.execSQL(sql);
            tvMsg.setText("Password Change");
            tvMsg.setTextColor(Color.GRAY);
        } catch (SQLiteException e) {
            tvMsg.setText(e.getMessage().toString());
        }

    }
}
