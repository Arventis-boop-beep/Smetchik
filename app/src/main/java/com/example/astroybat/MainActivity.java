package com.example.astroybat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recycler_view;
    SmetaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = findViewById(R.id.recycler_view);

        setRecyclerView();
    }

    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SmetaAdapter(this, getList());
        recycler_view.setAdapter(adapter)
        ;
    }

    private List<Smeta> getList(){
        List<Smeta> smetaList = new ArrayList<>();
        smetaList.add(new Smeta(null, null, 0, null,null,null,null,null));
        return smetaList;
    }

    void getAllSmetaCallback(Smeta smeta) {

    }
}

