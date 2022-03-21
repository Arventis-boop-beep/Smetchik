package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;


public class SmetaEdit extends AppCompatActivity {

    private native Smeta getSmeta(String uuid);

    Button backToMain;
    Button save;

    String uuid;
    Smeta smeta;

    EditText title, zakazchik, podryadchik, raboti, object, osnovanie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_edit);

        Intent intent_ = getIntent();
        uuid = intent_.getStringExtra("uuid");

        //Кнопка возврата
        backToMain = findViewById(R.id.back_to_MainActivity);
        backToMain.setOnClickListener(view -> closeSmetaEditActivity());


        //Кнопка сохранения
        save = findViewById(R.id.save_edited_smeta);

        title = findViewById(R.id.title);
        zakazchik = findViewById(R.id.zakazchik);
        podryadchik = findViewById(R.id.podryadchik);
        raboti = findViewById(R.id.raboti);
        object = findViewById(R.id.object);
        osnovanie = findViewById(R.id.osnovanie);

        save.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);

            smeta = getSmeta(uuid);

            smeta.title = title.getText().toString();
            smeta.zakazchik = zakazchik.getText().toString();
            smeta.podriadchik = podryadchik.getText().toString();
            smeta.raboti = raboti.getText().toString();
            smeta.obiekt = object.getText().toString();
            smeta.osnovaniye = osnovanie.getText().toString();

            startActivity(intent);
        });

    }

    private void closeSmetaEditActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

