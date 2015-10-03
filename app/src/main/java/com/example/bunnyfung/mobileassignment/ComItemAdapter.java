package com.example.bunnyfung.mobileassignment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bunnyfung on 6/7/15.
 */
public class ComItemAdapter extends ArrayAdapter<ComItem> implements View.OnClickListener {
    private int resource;
    private ArrayList<ComItem> comItems;
    ComItem comItem;

    public ComItemAdapter(Context context, int resource, ArrayList<ComItem> comItems) {
        super(context, resource, comItems);
        this.resource = resource;
        this.comItems = comItems;
    }

    public View getView (int position, final View convertView, ViewGroup parent){
        final LinearLayout itemView;
        comItem = comItems.get(position);

        if (convertView == null){
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }else {
            itemView = (LinearLayout)convertView;
        }
        TextView tvComNo = (TextView)itemView.findViewById(R.id.tvComNo);
        TextView tvComName = (TextView)itemView.findViewById(R.id.tvComName);
        final TextView tvComTel = (TextView)itemView.findViewById(R.id.tvComTel);
        final TextView tvComAddr = (TextView)itemView.findViewById(R.id.tvComAddr);
        ImageView ivPhone = (ImageView)itemView.findViewById(R.id.ivPhone);
        ImageView ivMap = (ImageView)itemView.findViewById(R.id.ivMap);

        tvComNo.setText(comItem.getComNo());
        tvComName.setText(comItem.getComName());
        tvComTel.setText(comItem.getComTel());
        tvComAddr.setText(comItem.getComAddr());

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvComTel.getText()));
                v.getContext().startActivity(intent);
            }
        });
        ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" +
                        tvComAddr.getText() + ""));
                v.getContext().startActivity(intent);
            }
        });


        return itemView;
    }

    @Override
    public void onClick(View v) {

    }
}
