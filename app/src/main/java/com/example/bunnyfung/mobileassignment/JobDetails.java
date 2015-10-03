package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class JobDetails extends Activity {
    TextView tvDetails;
    SQLiteDatabase db;
    int jobNo = 0;
    Cursor cursor;
    TextView tvRequestDate,tvJobNo,tvJobProblem,tvVisitDate,tvJobStatus,tvJobStartTime,
            tvJobEndTime,tvSerialNo,tvRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        tvRequestDate = (TextView)findViewById(R.id.tvRequestDate);
        tvJobNo = (TextView)findViewById(R.id.tvJobNo);
        tvJobProblem = (TextView)findViewById(R.id.tvJobProblem);
        tvVisitDate = (TextView)findViewById(R.id.tvVisitDate);
        tvJobStatus = (TextView)findViewById(R.id.tvJobStatus);
        tvJobStartTime = (TextView)findViewById(R.id.tvJobStartTime);
        tvJobEndTime = (TextView)findViewById(R.id.tvJobEndTime);
        tvSerialNo = (TextView)findViewById(R.id.tvSerialNo);
        tvRemark = (TextView)findViewById(R.id.tvRemark);

        Intent intent = getIntent();
        jobNo = Integer.parseInt(intent.getStringExtra("jobNo"));


        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("Select * from ServiceJob where jobNo="+ jobNo, null);
            while (cursor.moveToNext()) {
                int jobNumber = cursor.getInt(cursor.getColumnIndex("jobNo"));
                tvJobNo.setText(jobNumber+"");
                String requestDate = cursor.getString(cursor.getColumnIndex("requestDate"));
                tvRequestDate.setText(requestDate);
                String jobProblem = cursor.getString(cursor.getColumnIndex("jobProblem"));
                tvJobProblem.setText(jobProblem);
                String visitDate = cursor.getString(cursor.getColumnIndex("visitDate"));
                tvVisitDate.setText(visitDate);
                String jobStatus = cursor.getString(cursor.getColumnIndex("jobStatus"));
                tvJobStatus.setText(jobStatus);
                String jobStartTime = cursor.getString(cursor.getColumnIndex("jobStartTime"));
                tvJobStartTime.setText(jobStartTime);
                String jobEndTime = cursor.getString(cursor.getColumnIndex("jobEndTime"));
                tvJobEndTime.setText(jobEndTime);
                String serialNo = cursor.getString(cursor.getColumnIndex("serialNo"));
                tvSerialNo.setText(serialNo);
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                tvRemark.setText(remark);

            }
            db.close();
        }
        catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 1, 1, "Start");
        menu.add(0, 2, 2, "End");
        menu.add(0, 3, 3, "Remark");
        menu.add(0, 4, 4, "Add New Job");
        menu.add(0, 5, 5, "Cancel Job");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case 1:setStartTime();
                    finish();
                    startActivity(getIntent());
                    break;
            case 2:setEndTime();
                    finish();
                    startActivity(getIntent());
                    break;
            case 3:setRemark();
                    break;
            case 4:Intent intent = new Intent(this, AddNewJob.class);
                    startActivity(intent);
                    break;
            case 5:cancelJob();
                    finish();
                    break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setStartTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedTime = df.format(c.getTime());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyy");
        String formattedDate = df2.format(c.getTime());

        Toast.makeText(this, formattedTime+","+formattedDate, Toast.LENGTH_LONG).show();
        try{
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("UPDATE ServiceJob SET jobStartTime ='" + formattedTime + "',visitDate ='" +
                    formattedDate + "',jobStatus ='follow-up' WHERE jobNo=" + jobNo + ";");
            Toast.makeText(this, "Start Time Updated", Toast.LENGTH_LONG).show();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setEndTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedTime = df.format(c.getTime());
        String remark = "";

        Toast.makeText(this, formattedTime, Toast.LENGTH_LONG).show();
        try{
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("UPDATE ServiceJob SET jobEndTime ='"+formattedTime+"' WHERE jobNo="+jobNo+";");
            Toast.makeText(this, "End Time Updated", Toast.LENGTH_LONG).show();
            cursor = db.rawQuery("Select remark from ServiceJob where jobNo="+ jobNo, null);
            while (cursor.moveToNext()){
                remark = cursor.getString(cursor.getColumnIndex("remark"));
            }
            if(remark.equals("")){
                db.execSQL("UPDATE ServiceJob SET jobStatus ='completed' WHERE jobNo=" + jobNo + ";");
            }
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setRemark(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(JobDetails.this);
        alertDialog.setTitle("Remark");
        alertDialog.setMessage("Enter Remark");
        final EditText input = new EditText(JobDetails.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = input.getText().toString();
                Toast.makeText(JobDetails.this, str, Toast.LENGTH_LONG).show();
                try{
                    db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                            "/eBidDB", null, SQLiteDatabase.OPEN_READWRITE);
                    db.execSQL("UPDATE ServiceJob SET remark ='"+str+"',jobStatus ='follow-up' WHERE jobNo="+jobNo+";");
                    Toast.makeText(JobDetails.this, "Remark Updated", Toast.LENGTH_LONG).show();
                    db.close();
                }catch (SQLiteException e){
                    Toast.makeText(JobDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                finish();
                startActivity(getIntent());
            }
        });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }
    public void cancelJob(){
        try{
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment" +
                    "/eBidDB", null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("UPDATE ServiceJob SET jobStatus ='canceled' WHERE jobNo="+jobNo+";");
        }catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
