package com.myapplicationdev.android.p04_revisionnotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
	Song data;
	ListView lv;
	RevisionNotesArrayAdapter adapter;
	Button btnShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		lv = (ListView) this.findViewById(R.id.lv);
		btnShow = (Button)this.findViewById(R.id.btnShowSongs);

		final DBHelper dbh = new DBHelper(this);
		final ArrayList<Song> songs = dbh.getAllSongs();

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
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 9){
			btnShow.performClick();
		}
	}


}
