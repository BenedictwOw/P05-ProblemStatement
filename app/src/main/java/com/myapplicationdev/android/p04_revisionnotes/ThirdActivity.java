package com.myapplicationdev.android.p04_revisionnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    Song data;
    TextView tvID;
    EditText etTitle,etSingers,etYear;
    Button btnUpdate, btnDelete;
    RadioGroup rg;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tvID = findViewById(R.id.tvID);
        etTitle = findViewById(R.id.etTitle2);
        etSingers = findViewById(R.id.etSinger2);
        etYear = findViewById(R.id.etYear2);
        rg = (RadioGroup)findViewById(R.id.radioGroupStars2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");
        tvID.setText("ID: " + data.getId());
        etTitle.setText(data.getTitle());
        etSingers.setText(data.getSingers());
        etYear.setText(data.getYear());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSingers.getText().toString());
                data.setYear(etYear.getText().toString());
                int selectedButtonId = rg.getCheckedRadioButtonId();
                rb =  (RadioButton)findViewById(selectedButtonId);
                String value = rb.getText().toString();
                data.setStars(value);
                dbh.updateNote(data);
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteNote(data.getId());
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

}

