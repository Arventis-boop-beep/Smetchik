package com.example.astroybat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.astroybat.R;
import com.example.astroybat.classes.SmetaContentItem;

import java.util.ArrayList;

public class contentItemAdapter extends BaseAdapter {

    private ArrayList<SmetaContentItem> items;
    private LayoutInflater layoutInflater;

    public contentItemAdapter(Context context, ArrayList<SmetaContentItem> items){
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

        SmetaContentItem item = (SmetaContentItem) getContentItem(i);

        Button plus = (Button) view.findViewById(R.id.amount_plus);
        Button minus = (Button) view.findViewById(R.id.amount_minus);

        TextView full_title = view.findViewById(R.id.full_item_title);
        TextView title = view.findViewById(R.id.item_title);
        TextView unit = view.findViewById(R.id.item_unit);
        TextView amount = view.findViewById(R.id.item_amount);
        TextView price = view.findViewById(R.id.item_price);
        TextView overall = view.findViewById(R.id.item_overall);

        plus.setOnClickListener(view1 -> {
            plusAmount(item);
            notifyDataSetChanged();
        });
        minus.setOnClickListener(view1 -> {
            minusAmount(item);
            notifyDataSetChanged();
        });

        full_title.setText(item.title);
        title.append(item.title);
        unit.append(item.unit);
        price.append(Integer.toString(item.price));
        amount.append(Integer.toString(item.amount));
        overall.append(Integer.toString(item.amount * item.price));

        notifyDataSetChanged();

        return view;
    }

    private void plusAmount(SmetaContentItem item) {
        item.amount++;
    }

    private void minusAmount(SmetaContentItem item) {
        item.amount--;
    }

    private SmetaContentItem getContentItem(int i){
        return (SmetaContentItem) getItem(i);
    }
}
