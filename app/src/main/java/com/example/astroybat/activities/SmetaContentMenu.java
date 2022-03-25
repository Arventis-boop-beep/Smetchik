package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
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
    native void setStroybat(String filename);
    native void setStroybatData(String filename);
    native void getAllItemsForSmeta(String smeta_uuid);

    String stroybatDB, stroybatDataDB;

    Smeta smeta;
    String uuid;
    ArrayList<Item> items;

    Button add_button, back_to_main;
    TextView title;
    ListView contentView;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_content_menu);

        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        stroybatDB = "app/src/main/res/raw/stroybat.db";
        setStroybat(stroybatDB);

        stroybatDataDB = "app/src/main/res/raw/stroybat_data.db";
        setStroybatData(stroybatDataDB);


        smeta = getSmeta(uuid);
        title = findViewById(R.id.Smeta_title);
        title.setText(smeta.title);

        getAllItemsForSmeta(uuid);

        //Добавить услугу/материал
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {

        });

        //Назад на главную
        back_to_main = findViewById(R.id.back_button);
        back_to_main.setOnClickListener(view -> backToMain());

        //Список
        contentView = findViewById(R.id.content_lv);
        items = new ArrayList<>();
        adapter = new ItemAdapter(this, items);

        contentView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
    }

    private void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void getAllItemsForSmetaCallback(Item item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }

}