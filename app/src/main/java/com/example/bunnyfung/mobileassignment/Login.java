package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity implements View.OnClickListener {

    Cursor cursor=null;
    EditText etUsername, etPassword;
    Button btnLogin;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        String sql;
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB",
                    null, SQLiteDatabase.CREATE_IF_NECESSARY);
            // table Technician
            sql = "DROP TABLE if exists Technician;";
            db.execSQL(sql);
            sql = "CREATE TABLE Technician(" + "staffNo text PRIMARY KEY ," + "staffLogin text, "
                    + "staffPswd text, " + "staffName text); ";
            db.execSQL(sql);

            db.execSQL("INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName) values"
                    + "('1001', 'login1001', 'pass1001', 'Jacky Wong'); ");
            db.execSQL("INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName) values"
                    + "('1002', 'login1002', 'pass1002', 'Kevin Leung'); ");
            db.execSQL("INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName) values"
                    + "('1003', 'login1003', 'pass1003', 'Flora Chan'); ");
            db.execSQL("INSERT INTO Technician(staffNo, staffLogin, staffPswd, staffName) values"
                    + "('1004', 'login1004', 'pass1004', 'Alan Lam'); ");

            // table Product
            sql = "DROP TABLE if exists Product;";
            db.execSQL(sql);
            sql = "CREATE TABLE Product(" + "prodNo text PRIMARY KEY ," + "prodName text, "
                    + "prodPrice int); ";
            db.execSQL(sql);

            db.execSQL("INSERT INTO Product(prodNo, prodName, prodPrice) values"
                    + "('CN1008', 'Canon Power Photocopier', 4889); ");
            db.execSQL("INSERT INTO Product(prodNo, prodName, prodPrice) values"
                    + "('CN2186', 'Canon Inket Printer', 1635); ");
            db.execSQL("INSERT INTO Product(prodNo, prodName, prodPrice) values"
                    + "('HP1022', 'HP LaserJet 1022 Printer', 2500); ");
            db.execSQL("INSERT INTO Product(prodNo, prodName, prodPrice) values"
                    + "('HP2055', 'HP LaserJet Colour Printer', 3500); ");

            // table Company
            sql = "DROP TABLE if exists Company;";
            db.execSQL(sql);
            sql = "CREATE TABLE Company(" + "comNo text PRIMARY KEY ," + "comName text, "
                    + "comTel text, " + " comAddr text); ";
            db.execSQL(sql);

            db.execSQL("INSERT INTO Company(comNo, comName, comTel, comAddr) values"
                    + "('2001', 'Royal Pacific Hotel', '27368818', 'Royal Pacific Hotel & Towers, China Hong Kong City, 33 Canton Road, Tsim Sha Tsui'); ");
            db.execSQL("INSERT INTO Company(comNo, comName, comTel, comAddr) values"
                    + "('2002', 'Hang Seng Bank Ltd', '28220228', '83 Des Voeux Rd C, Central District'); ");
            db.execSQL("INSERT INTO Company(comNo, comName, comTel, comAddr) values"
                    + "('2003', 'American Express Bank Ltd', '22771010', 'One Exchange Square, Central District'); ");

            // table Purchase
            sql = "DROP TABLE if exists Purchase;";
            db.execSQL(sql);
            sql = "CREATE TABLE Purchase(" + "serialNo text PRIMARY KEY ," + "purchaseDate text, "
                    + "prodNo text, " + "comNo text); ";
            db.execSQL(sql);

            db.execSQL("INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) values"
                    + "(34738783298, '20042014', 'HP1022', '2003'); ");
            db.execSQL("INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) values"
                    + "(38473878893, '15082013', 'CN2186', '2002'); ");
            db.execSQL("INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) values"
                    + "(42389489993, '18122014', 'CN1008', '2003'); ");
            db.execSQL("INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) values"
                    + "(48947347894, '21022012', 'HP1022', '2002'); ");
            db.execSQL("INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) values"
                    + "(89347827894, '17012013', 'HP2055', '2002'); ");
            db.execSQL("INSERT INTO Purchase(serialNo, purchaseDate, prodNo, comNo) values"
                    + "(46917347228, '17012013', 'HP1022', '2001'); ");

            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onClick(View v) {
        db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB",
                null, SQLiteDatabase.OPEN_READONLY);
        boolean isValid=false;
        String myUsername=etUsername.getText().toString();
        String myPassword=etPassword.getText().toString();
        String name=null, password=null;
        cursor = db.rawQuery("select staffLogin, staffPswd from Technician", null);
        while (cursor.moveToNext()&&!isValid) {
            name = cursor.getString(cursor.getColumnIndex("staffLogin"));
            password = cursor.getString(cursor.getColumnIndex("staffPswd"));
            if(name.equals(myUsername)) {
                if (password.equals(myPassword))
                    isValid = true;
            }
        }
        db.close();
        if(isValid) {
            Intent i = new Intent(this, MenuActivity.class);
            i.putExtra("username",myUsername);
            startActivity(i);
        }else {
            Toast.makeText(this, "Invalid username/password", Toast.LENGTH_LONG).show();
            etUsername.setTextColor(Color.RED);
            etPassword.setTextColor(Color.RED);
        }
    }

}
