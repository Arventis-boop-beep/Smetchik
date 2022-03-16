package com.example.astroybat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SmetaContentMenu extends AppCompatActivity {

    private native int getSmeta(String uuid);

    Smeta smeta;
    String uuid;

    Button add_button, back_to_main;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_content_menu);

        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        getSmeta(uuid);
        title = findViewById(R.id.Smeta_title);
        title.setText(smeta.title);

        //Добавить услугу/материал
        add_button = findViewById(R.id.add_new_content);
        add_button.setOnClickListener(view -> {

        });

        //Назад на главную
        back_to_main = findViewById(R.id.back_to_Main_From_Content);
        back_to_main.setOnClickListener(view -> {
            backToMain();
        });

        //Список
    }

    private void getSmetaCallback(Smeta smeta_){
        smeta = smeta_;
    }

    private void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}