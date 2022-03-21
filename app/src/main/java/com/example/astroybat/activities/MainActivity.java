package com.example.astroybat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("astroybat");
    }

    private native int getAllSmeta();
    private native Smeta addNewSmeta();
    private native int removeSmeta(String uuid);

    ArrayList<String> smeta_titles;
    ArrayList<Smeta> smetas;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Получение списка смет
        smeta_titles = new ArrayList<>();
        getAllSmeta();

        ListView lvMain = findViewById(R.id.lv);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smeta_titles);

        lvMain.setAdapter(adapter);

        //Переход в меню сметы: услуги и материалы
        lvMain.setOnItemClickListener((adapterView, view, i, l) -> openSmetaContentMenu(i));

        //Контекстное меню удалить/редактировать
        registerForContextMenu(lvMain);

        //Добавить новую смету
        Button add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            smetas.add(addNewSmeta());
            smeta_titles.add(addNewSmeta().title);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.delete:
                removeSmeta(smetas.get(i.position).uuid);
                smetas.remove(i.position);
                smeta_titles.remove(i.position);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.edit:
                openSmetaEditActivity(i);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void openSmetaEditActivity(AdapterView.AdapterContextMenuInfo i) {
        Intent intent = new Intent(this, SmetaEdit.class);

        intent.putExtra("uuid", smetas.get(i.position).uuid);

        startActivity(intent);
    }

    private void openSmetaContentMenu(int position){
        Intent intent = new Intent(this, SmetaContentMenu.class);

        intent.putExtra("uuid", smetas.get(position).uuid);

        startActivity(intent);
    }

    void getAllSmetaCallback(Smeta smeta){
        smeta_titles.add(smeta.title);
        smetas.add(smeta);

        adapter.notifyDataSetChanged();
    }
}


