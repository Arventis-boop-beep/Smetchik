/*
 * File              : SmetaContentMenu.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.03.2022
 * Last Modified Date: 11.04.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.adapter.ItemAdapter;

import java.util.ArrayList;

public class SmetaContentMenu extends AppCompatActivity {

    private native Smeta getSmeta(String uuid);
    native void getAllItemsForSmeta(String smeta_uuid);

    native void removeItem(String item_uuid);

    Smeta smeta;
    String uuid;
    ArrayList<Item> items;

    Button add_button;
    TextView title;
    ListView contentView;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_content_menu);

        //get extra
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        //smeta init
        smeta = getSmeta(uuid);

        //Set title
        title = findViewById(R.id.Smeta_title);
        title.setText(smeta.title);

        //Getting items list
        getAllItemsForSmeta(uuid);

        //Список
        contentView = findViewById(R.id.content_lv);
        items = new ArrayList<>();
        adapter = new ItemAdapter(this, items);
        contentView.setAdapter(adapter);

        //context menu
        registerForContextMenu(contentView);

        //bottom back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_materials:
                openItemListActivity(uuid, -1, 0);
                break;
            case R.id.add_services:
                openItemListActivity(uuid, 0, 0);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.smeta_content_context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == R.id.delete_item) {
            removeItem(items.get(i.position).uuid);
            adapter.notifyDataSetChanged();
            return true;
        }
        else{
            return super.onContextItemSelected(item);
        }
    }

    private void openItemListActivity(String uuid, int database, int parent){
        Intent intent = new Intent(this, ItemList.class);
        intent.putExtra("database", database);
        intent.putExtra("parent", parent);
        intent.putExtra("uuid", uuid);
        startActivity(intent);
    }

    void getAllItemsForSmetaCallback(Item item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }

}
