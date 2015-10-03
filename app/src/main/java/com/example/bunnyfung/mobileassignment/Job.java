package com.example.bunnyfung.mobileassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Job extends Activity implements AdapterView.OnItemClickListener, View.OnFocusChangeListener, View.OnClickListener {
    SQLiteDatabase db;
    ListView lvList;
    private ArrayList<Item> data = new ArrayList<>();
    private ItemAdapter lvItemAdapter;
    String sorting = "";
    TextView tvMsg;
    Cursor cursor = null;
    String whereSQL = "";
    EditText etSearch;
    ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        tvMsg = (TextView)findViewById(R.id.tvMsg);
        lvList = (ListView)findViewById(R.id.lvList);
        etSearch = (EditText)findViewById(R.id.etSearch);
        ivSearch = (ImageView)findViewById(R.id.ivSearch);
        database();
        lvList.setOnItemClickListener(this);
        etSearch.setOnFocusChangeListener(this);
        ivSearch.setOnClickListener(this);

    }
    public void database(){
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("Select jobNo,requestDate,jobStatus,jobProblem from ServiceJob "+whereSQL+" Order By jobNo "+
                    sorting+"", null);
            while (cursor.moveToNext()) {
                int jobNo = cursor.getInt(cursor.getColumnIndex("jobNo"));
                String requestDate = cursor.getString(cursor.getColumnIndex("requestDate"));
                String jobStatus = cursor.getString(cursor.getColumnIndex("jobStatus"));
                String jobProblem = cursor.getString(cursor.getColumnIndex("jobProblem"));
                Item entry = new Item(jobNo, requestDate,jobStatus,jobProblem);
                data.add(entry);
            }
            lvItemAdapter = new ItemAdapter(this, R.layout.signle_item, data);
            lvList.setAdapter(lvItemAdapter);
        }
        catch (SQLiteException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            tvMsg.setText(e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
    public void loadSearch(String query){
        data = new ArrayList<>();
        whereSQL = "WHERE jobNo LIKE '%"+query+"%' OR requestDate LIKE '%"+query+"%' OR jobStatus LIKE '%"
                +query+"%' OR jobProblem LIKE '%"+query+"%'";
        database();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_refresh) {
            data = new ArrayList<>();
            database();
            startActivity(getIntent());
        }else if (item.getItemId() == R.id.action_sorting){
            if (sorting.equals("DESC")){
                sorting = "ASC";
            }else {
                sorting = "DESC";
            }
            data = new ArrayList<>();
            database();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = data.get(position).getJobNo()+"";
        Intent intent = new Intent(this, JobDetails.class);
        intent.putExtra("jobNo", selectedItem);
        startActivity(intent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ivSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivSearch){
            String s = etSearch.getText().toString();
            try{
                loadSearch(s);
            }catch (SQLiteException e){
                tvMsg.setText(e.getMessage().toString());
            }
        }
    }
}
