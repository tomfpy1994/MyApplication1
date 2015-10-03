package com.example.bunnyfung.mobileassignment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bunnyfung on 5/7/15.
 */
public class ItemAdapter extends ArrayAdapter<Item>{
    private int resource;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, int resource, ArrayList<Item> items){
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LinearLayout itemView;
        Item item = getItem(position);

        if (convertView == null){
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }else {
            itemView = (LinearLayout)convertView;
        }
        TextView tvJobNo = (TextView)itemView.findViewById(R.id.tvMsg1);
        TextView tvRequestDate = (TextView)itemView.findViewById(R.id.tvRequestDate);
        TextView tvJobStatus = (TextView)itemView.findViewById(R.id.tvJobStatus);
        TextView tvJobProblem = (TextView)itemView.findViewById(R.id.tvJobProblem);

        tvJobNo.setText(""+item.getJobNo());
        tvRequestDate.setText(item.getRequestDate());
        tvJobStatus.setText(item.getJobStatus());
        tvJobProblem.setText(item.getJobProblem());
        if (item.getJobStatus().equals("pending")){
            tvJobStatus.setTextColor(Color.RED);
        } else if (item.getJobStatus().equals("completed")){
            tvJobStatus.setTextColor(Color.GRAY);
        } else if (item.getJobStatus().equals("follow-up")){
            tvJobStatus.setTextColor(Color.BLUE);
        }

        return itemView;
    }
}
