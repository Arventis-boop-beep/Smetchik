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

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.classes.TextEditWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SmetaEdit extends AppCompatActivity {

    private native Smeta getSmeta(String database, String uuid);

    native public void smetaSetValueForKey(String database, String smeta_uuid, String value, String key);

    public String uuid;
    public Smeta smeta;

    TextInputLayout title, zakazchik, podryadchik, raboti, object, osnovanie;
    Button date_button;

    public String database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_edit);

        database = new File(getFilesDir(), "stroybat.db").getPath();
        Intent intent_ = getIntent();
        uuid = intent_.getStringExtra("uuid");

        //smeta init
        smeta = getSmeta(database, uuid);

        //TextEdits init
        title = findViewById(R.id.title);
        zakazchik = findViewById(R.id.zakazchik);
        podryadchik = findViewById(R.id.podryadchik);
        raboti = findViewById(R.id.raboti);
        object = findViewById(R.id.object);
        osnovanie = findViewById(R.id.osnovanie);
        date_button = findViewById(R.id.date_button);

        //TextEdits default meanings
        Objects.requireNonNull(title.getEditText()).setText(smeta.title);
        Objects.requireNonNull(zakazchik.getEditText()).setText(smeta.zakazchik);
        Objects.requireNonNull(podryadchik.getEditText()).setText(smeta.podriadchik);
        Objects.requireNonNull(raboti.getEditText()).setText(smeta.raboti);
        Objects.requireNonNull(object.getEditText()).setText(smeta.obiekt);
        Objects.requireNonNull(osnovanie.getEditText()).setText(smeta.osnovaniye);

        //save after text changed
        title.getEditText().addTextChangedListener(new TextEditWatcher("title",
                title.getEditText(), this));
        zakazchik.getEditText().addTextChangedListener(new TextEditWatcher("zakazchik",
                zakazchik.getEditText(), this));
        podryadchik.getEditText().addTextChangedListener(new TextEditWatcher("podryadchik",
                podryadchik.getEditText(), this));
        raboti.getEditText().addTextChangedListener(new TextEditWatcher("raboti",
                raboti.getEditText(), this));
        object.getEditText().addTextChangedListener(new TextEditWatcher("object",
                object.getEditText(), this));
        osnovanie.getEditText().addTextChangedListener(new TextEditWatcher("osnovanie",
                osnovanie.getEditText(), this));

        @SuppressLint("SimpleDateFormat") DateFormat dateformat = new SimpleDateFormat("dd:MM:yyyy");
        Date date = new Date(smeta.date * 1000L);
        date_button.setText(dateformat.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        date_button.setOnClickListener(view -> new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            calendar.set(i, i1, i2);
            date_button.setText(dateformat.format(calendar.getTime()));
            smetaSetValueForKey(database, uuid, "" + calendar.getTime().getTime()/1000,"date");
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());

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

// TODO: 12.05.2022 Заменить textedit на textinputlayout