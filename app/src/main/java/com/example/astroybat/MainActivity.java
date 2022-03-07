package com.example.astroybat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("astroybat");
    }

    native void getAllSmetaCallback(Smeta smeta);
    private native int getAllSmeta();
    private native int addNewSmeta();
    native void newSmetaCallback(Smeta smeta);
    private native int removeSmeta(String uuid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int count = getAllSmeta();
        Smeta smetas[] = new Smeta[count];
        for(int i = 0; i < count; i++){
            getAllSmetaCallback(smetas[i]);
        }

        ArrayList<String> smeta_titles = new ArrayList<>();
        for(int i = 0; i < count; i++){
            smeta_titles.add(smetas[i].title);
        }

        ListView lvMain = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, smeta_titles);

        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        Button add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSmetaAdditionAcivity();
            }
        });
    }

    private void openSmetaAdditionAcivity() {
        Intent intent = new Intent(this, SmetaAddition.class);
        startActivity(intent);
    }

//    private List<Smeta> getList(){
//        List<Smeta> smetaList = new ArrayList<>();
//        smetaList.add(new Smeta(
//                null,
//                null,
//                0,
//                null,
//                null,
//                null,
//                null,
//                null
//        ));
//        return smetaList;
//    }
//
//    void getAllSmetaCallback(Smeta smeta) {
//
//    }
}


