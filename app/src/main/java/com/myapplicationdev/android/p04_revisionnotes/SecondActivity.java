package com.myapplicationdev.android.p04_revisionnotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	Song data;
	ListView lv;
	RevisionNotesArrayAdapter adapter;
	Button btnShow;
	ArrayList<Song> songs;
	Spinner aSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		aSpinner = (Spinner)this.findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Spinner_items,android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		aSpinner.setAdapter(adapter2);

		aSpinner.setOnItemSelectedListener(this);

		lv = (ListView) this.findViewById(R.id.lv);
		btnShow = (Button)this.findViewById(R.id.btnShowSongs);

		final DBHelper dbh = new DBHelper(this);
		songs = dbh.getAllSongs();

		adapter = new RevisionNotesArrayAdapter(this, R.layout.row, songs);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();




		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int
					position, long identity) {
				data = songs.get(position);
				Intent i = new Intent(SecondActivity.this,
						ThirdActivity.class);
				i.putExtra("data", data);
				startActivityForResult(i, 9);
			}
		});
		btnShow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				songs.clear();
				DBHelper dbh = new DBHelper(SecondActivity.this);
				ArrayList<Song> data = dbh.getStars();
				for (int i = 0; i < data.size(); i++){
					songs.add(data.get(i));
				}
				adapter.notifyDataSetChanged();
				dbh.close();
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
									Intent data) {
		if (resultCode == RESULT_OK && requestCode == 9){
			DBHelper dbh = new DBHelper(SecondActivity.this);
			songs.clear();
			songs.addAll(dbh.getAllSongs());
			adapter.notifyDataSetChanged();
			dbh.close();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String text = parent.getItemAtPosition(position).toString();
		songs.clear();
		DBHelper dbh = new DBHelper(SecondActivity.this);
		ArrayList<Song> data = dbh.getYear(text);
		for (int i = 0; i < data.size(); i++){
			songs.add(data.get(i));
		}
		adapter.notifyDataSetChanged();
		dbh.close();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
