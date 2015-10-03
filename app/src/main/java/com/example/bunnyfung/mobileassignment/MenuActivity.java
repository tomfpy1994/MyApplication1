package com.example.bunnyfung.mobileassignment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MenuActivity extends Activity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    Cursor cursor=null;
    String username;
    private ArrayList<ItemM> data = new ArrayList<>();
    private String item[] = {"Product","Job","Company","Add New Job","Display graphic"};
    private MenuItemAdapter lvItemAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        database();
        ListView lvList = (ListView)findViewById(R.id.lvList);
        Intent intent=getIntent();
        for (int i=0; i<item.length; i++){
            ItemM entry = new ItemM(item[i].toString());
            data.add(entry);
        }
        lvItemAdapter = new MenuItemAdapter(this, R.layout.menu_item, data);
        lvList.setAdapter(lvItemAdapter);

        lvList.setOnItemClickListener(this);

    }

    public void database(){
        String result="";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://itd-moodle.ddns.me/ptms/service_job.php?staffNo=1001");
            HttpResponse response = client.execute(request);
            // Get the response
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            rd.close();
        }catch(Exception e){
            Toast.makeText(this, "can't connect to network", Toast.LENGTH_LONG).show();
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("ServiceJob");


            String sql = null;
            db = this.openOrCreateDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB"
                    , MODE_PRIVATE, null);
            // table jobService
            sql = "DROP TABLE if exists ServiceJob;";
            db.execSQL(sql);
            sql = "CREATE TABLE ServiceJob(jobNo int PRIMARY KEY, requestDate date, jobProblem text" +
                    ", visitDate date, jobStatus text, jobStartTime text, jobEndTime text, serialNo " +
                    "text, remark text);";
            db.execSQL(sql);
            int jobNo;
            String requestDate, jobProblem, visitDate, jobStatus, jobStartTime, jobEndTime, serialNo, remark;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                jobNo = item.getInt("jobNo");
                requestDate = item.getString("requestDate");
                jobProblem = item.getString("jobProblem");
                visitDate = item.getString("visitDate");
                jobStatus = item.getString("jobStatus");
                jobStartTime = item.getString("jobStartTime");
                jobEndTime = item.getString("jobEndTime");
                serialNo = item.getString("serialNo");
                remark = item.getString("remark");
                db.execSQL("INSERT INTO ServiceJob(jobNo, requestDate, jobProblem, visitDate" +
                        ", jobStatus, jobStartTime, jobEndTime, serialNo, remark) values"
                        + "(" + jobNo + ",'" + requestDate + "','" + jobProblem + "','" + visitDate + "','" + jobStatus + "','" +
                        jobStartTime + "','" + jobEndTime + "','" + serialNo + "','" + remark + "'); ");
            }
            db.close();
        }catch(Exception e){
            Toast.makeText(this, "can't open database", Toast.LENGTH_LONG).show();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        ActionBar actionBar = getActionBar();
        menu.add(0, 1, 1, "Change Password");
        actionBar.setDisplayShowTitleEnabled(true);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 1){
            Intent intent = new Intent(this, PassChange.class);
            startActivity(intent);
        }else if (id == R.id.action_logout){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0:intent = new Intent(this, Product.class);
                    break;
            case 1:intent = new Intent(this, Job.class);
                    break;
            case 2:intent = new Intent(this, Company.class);
                    break;
            case 3:intent = new Intent(this, AddNewJob.class);
                    break;
            case 4:intent = new Intent(this, Graph.class);
                    break;

        }
        startActivity(intent);
    }
}
