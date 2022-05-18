package com.example.astroybat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astroybat.R;
import com.example.astroybat.activities.SmetaContentMenu;
import com.example.astroybat.classes.Item;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

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

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //getting item
        Item item = getItem(position);

        //buttons init
        Button plus = convertView.findViewById(R.id.amount_plus);
        Button minus = convertView.findViewById(R.id.amount_minus);

        //text fields init
        TextView full_title = convertView.findViewById(R.id.full_item_title);
        TextInputLayout title = convertView.findViewById(R.id.item_title);
        TextInputLayout unit = convertView.findViewById(R.id.item_unit);
        TextInputLayout amount = convertView.findViewById(R.id.item_amount);
        TextInputLayout price = convertView.findViewById(R.id.item_price);
        TextInputLayout overall = convertView.findViewById(R.id.item_overall);

        //on click listeners init
        plus.setOnClickListener(view1 -> {
            plusAmount(item);
            SmetaContentMenu.itemSetValueForKey(SmetaContentMenu.database, item.uuid,
                    Integer.toString(item.count), "count");
            Objects.requireNonNull(price.getEditText()).setText(Integer.toString(item.count));
        });
        minus.setOnClickListener(view1 -> {
            minusAmount(item);
            SmetaContentMenu.itemSetValueForKey(SmetaContentMenu.database, item.uuid,
                    Integer.toString(item.count), "count");
            Objects.requireNonNull(price.getEditText()).setText(Integer.toString(item.count));
        });

        full_title.setText(item.title);

        Objects.requireNonNull(title.getEditText()).setText(item.title);
        Objects.requireNonNull(unit.getEditText()).setText(item.unit);
        Objects.requireNonNull(price.getEditText()).setText(Integer.toString(item.price));
        Objects.requireNonNull(amount.getEditText()).setText(Integer.toString(item.count));
        Objects.requireNonNull(overall.getEditText()).setText(Integer.toString(item.count * item.price));

        amount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                notifyDataSetChanged();
            }
        });
        price.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                notifyDataSetChanged();
            }
        });
        overall.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                notifyDataSetChanged();
            }
        });
        title.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void plusAmount(Item item) {
        item.count++;
    }

    private void minusAmount(Item item) {
        item.count--;
        if(item.count < 0)
            item.count = 0;
    }
}
