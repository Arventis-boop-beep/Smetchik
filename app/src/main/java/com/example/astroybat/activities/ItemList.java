package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    native void getAllItemsFromDatabaseForParent(int database, int parent);
    native Item addItemForSmeta(Item item, String smeta_uuid, int database);

    ListView lvItems;
    ArrayAdapter<String> adapter;
    ArrayList<Item> items;
    ArrayList<String> items_titles;
    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //getExtra uuid
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        //getting parent == NULL item list
        getAllItemsFromDatabaseForParent(0, 0);
        getAllItemsFromDatabaseForParent(-1, 0);

        //list view init
        lvItems = findViewById(R.id.items_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items_titles);
        lvItems.setAdapter(adapter);


        //Click on item func
        lvItems.setOnItemClickListener((adapterView, view, i, l) -> {

            Item item = items.get(i);
            if(item.id <= 0){
                addItemForSmeta(item, uuid, item.id);
                backToSmetaContentMenu();
            }
            else {
                items.clear();
                getAllItemsFromDatabaseForParent(item.id, item.id);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void backToSmetaContentMenu(){
        Intent intent = new Intent(this, SmetaContentMenu.class);
        startActivity(intent);
    }

    void getAllItemsFromDatabaseForParentCallback(Item item){
        items.add(item);
        items_titles.add(item.title);
    }
}