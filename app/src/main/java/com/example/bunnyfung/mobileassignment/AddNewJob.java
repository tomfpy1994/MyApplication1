package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddNewJob extends Activity implements View.OnClickListener {
    EditText etProblem,etSeralNo,etRmark;
    Button btnCancel,btnAdd;
    TextView tvRemark,tvSeralNo,tvProblem;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job);
        etProblem = (EditText)findViewById(R.id.etProblem);
        etRmark = (EditText)findViewById(R.id.etRmark);
        etSeralNo = (EditText)findViewById(R.id.etSeralNo);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_job, menu);
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

    @Override
    public void onClick(View v) {
        int jobNo = 0;
        String requestDate,jobProblem,visitDate,jobStartTime,jobEndTime,serialNo,remark;
        String jobStatus = "'pending'";
        if (v.getId() == R.id.btnCancel){
            finish();
        }else if (v.getId() == R.id.btnAdd){
            Cursor cursor=null;
            try {
                db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                        "/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
                cursor = db.rawQuery("Select jobNo from ServiceJob", null);
                while (cursor.moveToLast()){
                    jobNo = cursor.getInt(cursor.getColumnIndex("jobNo"));
                    break;
                }
                db.close();
                jobNo++;
                Toast.makeText(this, ""+jobNo, Toast.LENGTH_LONG).show();

            }catch (SQLiteException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(c.getTime());
            requestDate = "'"+formattedDate+"'";
            jobProblem = "'"+etProblem.getText().toString()+"'";
            visitDate = "''";
            jobStartTime = "''";
            jobEndTime = "''";
            serialNo = "'"+etSeralNo.getText().toString()+"'";
            remark = "'"+etRmark.getText().toString()+"'";
            String sqlStr = "INSERT INTO ServiceJob(jobNo, requestDate,jobProblem, visitDate, "+
                    "jobStatus, jobStartTime,jobEndTime, serialNo,remark) values ("+jobNo+","+
                    requestDate+","+jobProblem+","+visitDate+","+jobStatus+","+jobStartTime+","+
                    jobEndTime+","+serialNo+","+remark+" )";
            try{
                db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                        "/eBidDB", null, SQLiteDatabase.OPEN_READWRITE);
                db.execSQL(sqlStr);
                db.close();
                Toast.makeText(this, "A new job added", Toast.LENGTH_LONG).show();
            }catch (SQLiteException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            finish();

        }
    }
}
