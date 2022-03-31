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
    int parent, database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //getExtra uuid
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        parent = intent.getIntExtra("parent", 0);
        database = intent.getIntExtra("database", 0);

        //getting parent == NULL item list
        getAllItemsFromDatabaseForParent(database, parent);

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
                Intent new_intent = new Intent(this, ItemList.class);
                new_intent.putExtra("uuid", uuid);
                new_intent.putExtra("database", database);
                new_intent.putExtra("parent", item.id);
                startActivity(new_intent);
            }
        });
    }

    private void backToSmetaContentMenu(){
        Intent intent = getParentActivityIntent();
        startActivity(intent);
    }

    void getAllItemsFromDatabaseForParentCallback(Item item){
        items.add(item);
        items_titles.add(item.title);
    }
}