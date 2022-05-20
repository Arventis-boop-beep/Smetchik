/*
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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.astroybat.R;
import com.example.astroybat.classes.Smeta;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("astroybat");
    }

	String database;

    private native void getAllSmeta(String database);
    private native Smeta addNewSmeta(String database);
    private native int removeSmeta(String database, String uuid);

    ArrayList<String> smeta_titles;
    ArrayList<Smeta> smetas;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		//copy files from resources
		copyRawFiles();

        //init arrays
        smeta_titles = new ArrayList<>();
		smetas = new ArrayList<>();

		//init list view
		ListView lvMain = findViewById(R.id.lv);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smeta_titles);
		lvMain.setAdapter(adapter);
		adapter.notifyDataSetChanged();

        //Получение списка смет
		getAllSmeta(database);

		//Переход в меню сметы: услуги и материалы
		lvMain.setOnItemClickListener((adapterView, view, i, l) -> openSmetaContentMenu(i));

        //Контекстное меню удалить/редактировать
		registerForContextMenu(lvMain);
    }

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.top_menu_for_main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.add_button) {
			addButtonPushed();
		}
		else{
			return super.onOptionsItemSelected(item);
		}
		return true;
	}	

	void addButtonPushed() {
			Smeta new_smeta = addNewSmeta(database);
			smetas.add(new_smeta);

			new_smeta.title = "Новая смета";
			new_smeta.zakazchik = " ";
			new_smeta.podriadchik = " ";
			new_smeta.raboti = " ";
			new_smeta.obiekt = " ";
			new_smeta.osnovaniye = " ";

			smeta_titles.add(new_smeta.title);
			adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.main_activity_context_menu, menu);
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public boolean onContextItemSelected(@NonNull MenuItem item) {
		AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		switch(item.getItemId()){
			case R.id.delete:
				removeSmeta(database, smetas.get(i.position).uuid);
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
		if(!stroybat_database.exists()) { //don't overwrite file
			InputStream stroybatDB = resources.openRawResource(R.raw.stroybat);
			try {
				copy(stroybatDB, stroybat_database);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		database = stroybat_database.getPath();
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


