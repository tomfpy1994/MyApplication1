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
 * Created by bunnyfung on 6/7/15.
 */
public class ProdItemAdapter extends ArrayAdapter<ProdItem>{
    private int resource;
    private ArrayList<ProdItem> prodItems;

    public ProdItemAdapter(Context context, int resource, ArrayList<ProdItem> prodItems) {
        super(context, resource, prodItems);
        this.resource = resource;
        this.prodItems = prodItems;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LinearLayout itemView;
        ProdItem prodItem = getItem(position);

        if (convertView == null){
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }else {
            itemView = (LinearLayout)convertView;
        }
        TextView tvProdNum = (TextView)itemView.findViewById(R.id.tvProdNum);
        TextView tvProdName = (TextView)itemView.findViewById(R.id.tvProdName);


        tvProdNum.setText(prodItem.getProdNo());
        tvProdName.setText(prodItem.getProdName());


        return itemView;
    }
}
