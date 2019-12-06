package com.example.jisu.musicproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DataBase extends AppCompatActivity implements View.OnClickListener {
    ArrayList<DataList> list = new ArrayList<>();
    RecyclerView recycler;
    Button btnInit, btnClose, btnSelect;
    MyAdapter adapter;
    LinearLayoutManager manager;
    MyDB mydb;
    SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);
        setTitle("데이터베이스");
        recycler = findViewById(R.id.recycler);
        btnInit = findViewById(R.id.btnInit);
        btnSelect = findViewById(R.id.btnSelect);
        btnClose = findViewById(R.id.btnClose);

        adapter = new MyAdapter(R.layout.item_list2, list);
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        mydb = new MyDB(this);
        btnInit.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnSelect.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Cursor cursor=null;
        switch (v.getId()) {
            case R.id.btnInit:
                sqlDB = mydb.getWritableDatabase();
                mydb.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
                break;
            case R.id.btnSelect:
                sqlDB = mydb.getWritableDatabase();
                cursor = sqlDB.rawQuery("SELECT * FROM musicTBL;", null);
                list.removeAll(list);
                while (cursor.moveToNext()) {
                    DataList datalist = new DataList();
                    String name = cursor.getString(0);
                    String artist = cursor.getString(1);
                    int score = cursor.getInt(2);
                    datalist.setName(name);
                    datalist.setArtist(artist);
                    datalist.setScroe(score);
                    Log.e("MainActivity", datalist.toString());
                    list.add(datalist);
                }
                adapter.notifyDataSetChanged();
                cursor.close();
                sqlDB.close();
                break;
            case R.id.btnClose:
                finish();
                break;
        }
    }
}
