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
import android.widget.EditText;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.classes.TextEditWatcher;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SmetaEdit extends AppCompatActivity {

    private native Smeta getSmeta(String database, String uuid);

    native public void setSmetaValueForKey(String database, String uuid, String value, String key);

    public String uuid;
    public Smeta smeta;

    EditText title, zakazchik, podryadchik, raboti, object, osnovanie;
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

        @SuppressLint("SimpleDateFormat") DateFormat dateformat = new SimpleDateFormat("dd:MM:yyyy");
        Date date = new Date(smeta.date * 1000L);
        date_button.setText(dateformat.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        date_button.setOnClickListener(view -> new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            calendar.set(i, i1, i2);
            date_button.setText(dateformat.format(calendar.getTime()));
            setSmetaValueForKey(database, uuid, "" + calendar.getTime().getTime()/1000,"date");
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

