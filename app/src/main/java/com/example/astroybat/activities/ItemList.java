package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;

public class ItemList extends AppCompatActivity {

    native void getAllItemsFromDatabaseForParent(int database, int parent);
    native void getAllItemsFromDatabaseForParentCallback(Item item);
    native Item addItemForSmeta(Item item, String smeta_uuid, int database);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

    }
}