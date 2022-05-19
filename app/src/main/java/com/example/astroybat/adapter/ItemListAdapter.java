package com.example.astroybat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astroybat.R;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<String> {

    Context mContext;
    int mResource;
    ArrayList<String> item_titles;

    public ItemListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.item_titles = objects;
    }

    public String getTitle(int position) {
        return item_titles.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //getting title
        String itemTitle  = getTitle(position);

        //TextView init
        TextView title = convertView.findViewById(R.id.title_in_list);

        title.setText(itemTitle);

        return convertView;
    }
}
