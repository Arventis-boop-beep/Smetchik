package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.classes.SmetaContentItem;
import com.example.astroybat.adapter.contentItemAdapter;

import java.util.ArrayList;

public class SmetaContentMenu extends AppCompatActivity {

    private native Smeta getSmeta(String uuid);

    Smeta smeta;
    String uuid;
    ArrayList<SmetaContentItem> items;

    Button add_button, back_to_main;
    TextView title;
    ListView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_content_menu);

        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        smeta = getSmeta(uuid);
        title = findViewById(R.id.Smeta_title);
        title.setText(smeta.title);

        //Добавить услугу/материал
        add_button = findViewById(R.id.add_new_content);
        add_button.setOnClickListener(view -> {

        });

        //Назад на главную
        back_to_main = findViewById(R.id.back_to_Main_From_Content);
        back_to_main.setOnClickListener(view -> backToMain());

        //Список
        contentView = findViewById(R.id.content_lv);
        items = new ArrayList<>();
        contentItemAdapter adapter = new contentItemAdapter(this, items);

        contentView.setAdapter(adapter);
    }

    private void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}