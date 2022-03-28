/**
 * File              : MainActivity.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.03.2022
 * Last Modified Date: 28.03.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("astroybat");
    }

    private native int getAllSmeta();
    private native Smeta addNewSmeta();
    private native int removeSmeta(String uuid);
    native void setStroybat(String filename);
    native void setStroybatData(String filename);

    ArrayList<String> smeta_titles;
    ArrayList<Smeta> smetas;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init arrays
        smeta_titles = new ArrayList<String>();
		smetas = new ArrayList<Smeta>();

		//init list view
		ListView lvMain = findViewById(R.id.lv);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smeta_titles);
		lvMain.setAdapter(adapter);

		//copy files from resources
		copyRawFiles();

        //Получение списка смет
		getAllSmeta();

		//Переход в меню сметы: услуги и материалы
		lvMain.setOnItemClickListener((adapterView, view, i, l) -> openSmetaContentMenu(i));

        //Контекстное меню удалить/редактировать
		registerForContextMenu(lvMain);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.top_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_button:
				addButtonPushed();
			default:
				return super.onOptionsItemSelected(item);
		}
	}	

	void addButtonPushed() {
			Smeta new_smeta = addNewSmeta();
			smetas.add(new_smeta);
			smeta_titles.add(new_smeta.title);
			adapter.notifyDataSetChanged();
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

	void copyRawFiles(){
		Context context = getApplicationContext();
        Resources resources = this.getResources();
		
		File stroybat_database = new File(context.getFilesDir(), "stroybat.db");
		if(stroybat_database.exists()){ //don't overwrite file
		} else {
			InputStream stroybatDB = resources.openRawResource(R.raw.stroybat);
			try {
				copy(stroybatDB, stroybat_database);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setStroybat(stroybat_database.getPath());

		File stroybat_data_database = new File(context.getFilesDir(), "stroybat_data.db");
		InputStream stroybatDataDB = resources.openRawResource(R.raw.stroybat_data);		
		try {
			copy(stroybatDataDB, stroybat_data_database);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setStroybatData(stroybat_data_database.getPath());
	}

	public static void copy(InputStream in, File dst) throws IOException {
		try (OutputStream out = new FileOutputStream(dst)) {
			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		}
	}	
}


