/*
 * File              : SmetaEdit.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.03.2022
 * Last Modified Date: 28.03.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;

import java.util.Date;
import java.sql.Timestamp;


public class SmetaEdit extends AppCompatActivity {

    private native Smeta getSmeta(String uuid);

    native void setSmetaTitle(String smeta_uuid, String title);

    native void setSmetaZakazchik(String smeta_uuid, String zakazchik);

    native void setSmetaPodryadchik(String smeta_uuid, String podryadchik);

    native void setSmetaRaboti(String smeta_uuid, String raboti);

    native void setSmetaObject(String smeta_uuid, String object);

    native void setSmetaOsnovanie(String smeta_uuid, String osnovanie);

    native void setSmetaDate(String smeta_uuid, long date);

    String uuid;
    Smeta smeta;

    EditText title, zakazchik, podryadchik, raboti, object, osnovanie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_edit);

        Intent intent_ = getIntent();
        uuid = intent_.getStringExtra("uuid");

        //smeta init
        smeta = getSmeta(uuid);

        //TextEdits init
        title = findViewById(R.id.title);
        zakazchik = findViewById(R.id.zakazchik);
        podryadchik = findViewById(R.id.podryadchik);
        raboti = findViewById(R.id.raboti);
        object = findViewById(R.id.object);
        osnovanie = findViewById(R.id.osnovanie);

        //TextEdits default meanings
        title.setText(smeta.title);
        zakazchik.setText(smeta.zakazchik);
        podryadchik.setText(smeta.podriadchik);
        raboti.setText(smeta.raboti);
        object.setText(smeta.obiekt);
        osnovanie.setText(smeta.osnovaniye);
    }

    @Override
    protected void onStop() {
        super.onStop();

        setSmetaTitle(uuid, title.getText().toString());
        setSmetaZakazchik(uuid, zakazchik.getText().toString());
        setSmetaPodryadchik(uuid, podryadchik.getText().toString());
        setSmetaRaboti(uuid, raboti.getText().toString());
        setSmetaObject(uuid, object.getText().toString());
        setSmetaOsnovanie(uuid, osnovanie.getText().toString());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        setSmetaDate(uuid, timestamp.getTime());
    }

    @Override
    protected void onPause() {
        super.onPause();

        setSmetaTitle(uuid, title.getText().toString());
        setSmetaZakazchik(uuid, zakazchik.getText().toString());
        setSmetaPodryadchik(uuid, podryadchik.getText().toString());
        setSmetaRaboti(uuid, raboti.getText().toString());
        setSmetaObject(uuid, object.getText().toString());
        setSmetaOsnovanie(uuid, osnovanie.getText().toString());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        setSmetaDate(uuid, timestamp.getTime());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);

        return true;
    }
}

