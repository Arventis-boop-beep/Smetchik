package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.astroybat.R;

public class ItemList extends AppCompatActivity {

    native void setStroybat(String filename);
    native void setStroybatData(String filename);
    String stroybatDB, stroybatDataDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        stroybatDB = "app/src/main/res/raw/stroybat.db";
        setStroybat(stroybatDB);

        stroybatDataDB = "app/src/main/res/raw/stroybat_data.db";
        setStroybatData(stroybatDataDB);
    }
}