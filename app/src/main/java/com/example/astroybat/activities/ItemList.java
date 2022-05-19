/*
 * File              : ItemList.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 15.04.2022
 * Last Modified Date: 15.04.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.astroybat.R;
import com.example.astroybat.adapter.ItemListAdapter;
import com.example.astroybat.classes.Item;

import java.io.File;
import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    native void getAllItemsFromDatabaseForParent(String database, int datatype, int parent);
    native Item addItemForSmeta(String database, Item item, String smeta_uuid, int data_type);

    ListView lvItems;
    ItemListAdapter adapter;
    ArrayList<Item> items;
    ArrayList<String> items_titles;
    String uuid;
    int parent, datatype;
    public String database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        database = new File(this.getFilesDir(), "stroybat.db").getPath();

        //getExtra uuid
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        parent = intent.getIntExtra("parent", 0);
        datatype = intent.getIntExtra("datatype", 0);

		//init Arrays
		items = new ArrayList<>();
		items_titles = new ArrayList<>();

        //getting parent == NULL item list
        getAllItemsFromDatabaseForParent(database, datatype, parent);

        //list view init
        lvItems = findViewById(R.id.items_list);
        adapter = new ItemListAdapter(this, R.layout.item_list_layout, items_titles);
        lvItems.setAdapter(adapter);


        //Click on item func
        lvItems.setOnItemClickListener((adapterView, view, i, l) -> {

            Item item = items.get(i);
            if(item.id <= 0){
                addItemForSmeta(database, item, uuid, item.id);
                backToSmetaContentMenu(uuid);
            }
            else {
                Intent new_intent = new Intent(this, ItemList.class);
                new_intent.putExtra("uuid", uuid);
                new_intent.putExtra("datatype", datatype);
                new_intent.putExtra("parent", item.id);
                startActivity(new_intent);
            }
        });

        //bottom back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent parent_intent = getParentActivityIntent();
                parent_intent.putExtra("uuid", uuid);
                parent_intent.putExtra("parent", parent);
                parent_intent.putExtra("datatype", datatype);

                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        else{
        return super.onOptionsItemSelected(item);
    }
    }


    private void backToSmetaContentMenu(String uuid){
        Intent intent = getParentActivityIntent();
        intent.putExtra("uuid", uuid);
        startActivity(intent);
    }

    void getAllItemsFromDatabaseForParentCallback(Item item){
        items.add(item);
        items_titles.add(item.title);
    }
}
