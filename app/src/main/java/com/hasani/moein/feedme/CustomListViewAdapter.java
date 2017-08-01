package com.hasani.moein.feedme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Moein on 8/1/2017.
 */


public class CustomListViewAdapter extends ArrayAdapter<Item> {

    private int Layoutresource;
    private Activity activity;
    private ArrayList<Item> Item_list=new ArrayList<>();

    public CustomListViewAdapter(Activity act,int resource,
                                 ArrayList<Item> data)
    {
        super(act,resource,data);
        Layoutresource =resource;
        activity = act;
        Item_list = data;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return Item_list.size();
    }

    @Override
    public Item getItem(int position) {
        return Item_list.get(position);
    }

    @Override
    public int getPosition(Item item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        Viewholder holder=null;

        if (row == null||(row.getTag()==null)) {
            LayoutInflater inflater= LayoutInflater.from(activity);
            row=inflater.inflate(Layoutresource,null);

            holder=new Viewholder();

            holder.title=(TextView)row.findViewById(R.id.title);
            holder.pubdate=(TextView)row.findViewById(R.id.pubdate);

            row.setTag(holder);

        }else{
            holder=(Viewholder)row.getTag();
        }

        holder.item=getItem(position);

        holder.pubdate.setText(holder.item.getPubdate());
        holder.title.setText(String.valueOf(holder.item.getTitle()));

        final Viewholder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(activity,Details_activity.class);

                Bundle mBundle=new Bundle();
                mBundle.putSerializable("My object", finalHolder.item);
                i.putExtras(mBundle);
                activity.startActivity(i);

            }
        });
        return row;
    }


    public class Viewholder{
        Item item;
        TextView pubdate;
        TextView title;
    }
}
