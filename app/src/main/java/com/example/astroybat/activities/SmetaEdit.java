/*
 * File              : SmetaEdit.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.03.2022
 * Last Modified Date: 28.03.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.classes.TextEditWatcher;

import java.util.Date;
import java.sql.Timestamp;


public class SmetaEdit extends AppCompatActivity {

    private native Smeta getSmeta(String uuid);

    native public void setSmetaTitle(String smeta_uuid, String title);

    native public void setSmetaZakazchik(String smeta_uuid, String zakazchik);

    native public void setSmetaPodryadchik(String smeta_uuid, String podryadchik);

    native public void setSmetaRaboti(String smeta_uuid, String raboti);

    native public void setSmetaObject(String smeta_uuid, String object);

    native public void setSmetaOsnovanie(String smeta_uuid, String osnovanie);

    native public void setSmetaDate(String smeta_uuid, long date);

    public String uuid;
    public Smeta smeta;

    EditText title, zakazchik, podryadchik, raboti, object, osnovanie, date;


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
        date = findViewById(R.id.date);

        //TextEdits default meanings
        title.setText(smeta.title);
        zakazchik.setText(smeta.zakazchik);
        podryadchik.setText(smeta.podriadchik);
        raboti.setText(smeta.raboti);
        object.setText(smeta.obiekt);
        osnovanie.setText(smeta.osnovaniye);

        //save after text changed
        title.addTextChangedListener(new TextEditWatcher("title", title, this));
        zakazchik.addTextChangedListener(new TextEditWatcher("zakazchik", zakazchik, this));
        podryadchik.addTextChangedListener(new TextEditWatcher("podryadchik", podryadchik, this));
        raboti.addTextChangedListener(new TextEditWatcher("raboti", raboti, this));
        object.addTextChangedListener(new TextEditWatcher("object", object, this));
        osnovanie.addTextChangedListener(new TextEditWatcher("osnovanie", osnovanie, this));
        date.addTextChangedListener(new TextEditWatcher("date", date, this));

        //bottom back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }
}

