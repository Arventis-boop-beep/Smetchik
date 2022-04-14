package com.example.astroybat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private final ArrayList<Item> items;
    private final LayoutInflater layoutInflater;

    public ItemAdapter(Context context, ArrayList<Item> items){
        this.items = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = layoutInflater.inflate(R.layout.content_item_layout, viewGroup, false);
        }

        Item item = getContentItem(i);

        Button plus = view.findViewById(R.id.amount_plus);
        Button minus = view.findViewById(R.id.amount_minus);

        TextView full_title = view.findViewById(R.id.full_item_title);
        TextView title = view.findViewById(R.id.item_title);
        TextView unit = view.findViewById(R.id.item_unit);
        TextView amount = view.findViewById(R.id.item_amount);
        TextView price = view.findViewById(R.id.item_price);
        TextView overall = view.findViewById(R.id.item_overall);

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

        notifyDataSetChanged();

        return view;
    }

    private void plusAmount(Item item) {
        item.count++;
    }

    private void minusAmount(Item item) {
        item.count--;
        if(item.count < 0)
            item.count = 0;
    }

    private Item getContentItem(int i){
        return (Item) getItem(i);
    }
}
