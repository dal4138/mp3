package com.example.jisu.musicproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSelect,btnClose;
    EditText edtScore,edtArtist;
    TextView txtName;
    MyDB mydb;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_music);
        setTitle("등록창");
        btnSelect=findViewById(R.id.btnSelect);
        btnClose=findViewById(R.id.btnClose);
        txtName=findViewById(R.id.txtName);
        edtArtist=findViewById(R.id.edtArtist);
        edtScore=findViewById(R.id.edtScore);

        mydb=new MyDB(this);
        Intent intent=getIntent();
        String str=intent.getStringExtra("name");
        txtName.setText(str);
        btnClose.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelect :
                sqlDB=mydb.getWritableDatabase();
                if (txtName.getText().toString().trim() !="") {
                    sqlDB.execSQL("INSERT INTO musicTBL VALUES('" + txtName.getText().toString() + "','" +
                           edtArtist.getText().toString() + "'," + Integer.parseInt(edtScore.getText().toString()) + ");");
                }
                sqlDB.close();
                break;
            case R.id.btnClose :finish(); break;
        }
    }
}
