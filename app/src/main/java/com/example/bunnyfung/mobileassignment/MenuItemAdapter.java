package com.example.bunnyfung.mobileassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BunnyAMD on 2015/7/9.
 */
public class MenuItemAdapter extends ArrayAdapter<ItemM> {
    private int resource;
    private ArrayList<ItemM> itemMs;
    ItemM itemM;

    public MenuItemAdapter(Context context, int resource, ArrayList<ItemM> mItems) {
        super(context, resource, mItems);
        this.resource = resource;
        this.itemMs = mItems;
    }
    public View getView (int position, final View convertView, ViewGroup parent) {
        final LinearLayout itemView;
        itemM = itemMs.get(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }
        TextView tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
        tvTitle.setText(itemM.getItem());
        return itemView;
    }
}
