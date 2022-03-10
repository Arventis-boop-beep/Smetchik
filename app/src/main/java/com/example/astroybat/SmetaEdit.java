package com.example.astroybat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SmetaEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_edit);

        Button backToMain = (Button) findViewById(R.id.back_to_MainActivity);
        backToMain.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeSmetaEditActivity();
            }
        });
    }

    private void closeSmetaEditActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}