package com.example.astroybat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item>{

        private final Context mContext;
        int mResource;
        ArrayList<Item> items;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        items = objects;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        Item item = getContentItem(position);

        Button plus = convertView.findViewById(R.id.amount_plus);
        Button minus = convertView.findViewById(R.id.amount_minus);

        TextView full_title = convertView.findViewById(R.id.full_item_title);
        TextView title = convertView.findViewById(R.id.item_title);
        TextView unit = convertView.findViewById(R.id.item_unit);
        TextView amount = convertView.findViewById(R.id.item_amount);
        TextView price = convertView.findViewById(R.id.item_price);
        TextView overall = convertView.findViewById(R.id.item_overall);

        CharSequence price_s = price.getText();

        plus.setOnClickListener(view1 -> {
            plusAmount(item);
            price.setText(price_s);
            price.append(Integer.toString(item.count));
            notifyDataSetChanged();
        });
        minus.setOnClickListener(view1 -> {
            minusAmount(item);
            price.setText(price_s);
            price.append(Integer.toString(item.count));
            notifyDataSetChanged();
        });

        full_title.setText(item.title);

        title.append(item.title);
        unit.append(item.unit);
        price.append(Integer.toString(item.price));
        amount.append(Integer.toString(item.count));
        overall.append(Integer.toString(item.count * item.price));

        return convertView;
    }

    private void plusAmount(Item item) {
        item.count++;
        notifyDataSetChanged();
    }

    private void minusAmount(Item item) {
        item.count--;
        if(item.count < 0)
            item.count = 0;
        notifyDataSetChanged();
    }

    private Item getContentItem(int i){
        return (Item) getItem(i);
    }
}



