/*
 * File              : SmetaContentMenu.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.03.2022
 * Last Modified Date: 14.05.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.adapter.ItemAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SmetaContentMenu extends AppCompatActivity {

    private native Smeta getSmeta(String database, String uuid);
    native void getAllItemsForSmeta(String database, String smeta_uuid);
    native int generateXLSX(String database, String smeta_uuid, String filepath);
    native void removeItem(String database, String item_uuid);
    native void getAllItemsFromDatabaseForParent(String database, int data_type, int parent);

    Smeta smeta;
    String uuid;
    ArrayList<Item> items;
    public String database;
    private final static String TAG = "SmetaContentMenu";

    TextView title;
    ListView contentView;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate: started.");
        setContentView(R.layout.activity_smeta_content_menu);

        database = new File(this.getFilesDir(), "stroybat.db").getPath();

        //get extra
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        //smeta init
        smeta = getSmeta(database, uuid);

        //Set title
        title = findViewById(R.id.Smeta_title);
        title.setText(smeta.title);

        //Список
        contentView = findViewById(R.id.content_lv);
        items = new ArrayList<>();
        adapter = new ItemAdapter(this, R.layout.content_item_layout, items);
        contentView.setAdapter(adapter);
        
		//Getting items list
        getAllItemsForSmeta(database, uuid);

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
        getMenuInflater().inflate(R.menu.top_menu_for_content, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.print:
                PrintSmeta(uuid);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_materials:
                openItemListActivity(uuid, -1);
                break;
            case R.id.add_services:
                openItemListActivity(uuid, 0);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void PrintSmeta(String uuid) {
        // TODO: 12.05.2022
        /* Создать файл в стандартной папке
        Переписать темплэйтом из ресурсов
        Запустить JNI функцию для принта
        Открыть интент этого файла -> setAction setData and Type (есть в телеге)
         */
        File table  = new File(getApplicationContext().getFilesDir(), "Template.xlsx");
        Resources resources = this.getResources();

        if(!table.exists()) { //don't overwrite file
            InputStream tableIn = resources.openRawResource(R.raw.template);
            try {
                MainActivity.copy(tableIn, table);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        generateXLSX(database, uuid, table.getPath());

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);

        Uri uri = Uri.parse(table.getAbsolutePath());

        intent.setDataAndType(uri, "application/vnd.ms-excel");
        startActivity(intent);

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
            removeItem(database, items.get(i.position).uuid);
            adapter.notifyDataSetChanged();
            return true;
        }
        else{
            return super.onContextItemSelected(item);
        }
    }

    private void openItemListActivity(String uuid, int datatype){
        Intent intent = new Intent(this, ItemList.class);
        intent.putExtra("datatype", datatype);
        intent.putExtra("parent", 0);
        intent.putExtra("uuid", uuid);
        startActivity(intent);
    }

    void getAllItemsForSmetaCallback(Item item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }

}

// TODO: 13.05.2022 Вставить и проверить новые jni функции из телеги
