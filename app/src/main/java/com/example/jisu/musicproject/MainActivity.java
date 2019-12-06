package com.example.jisu.musicproject;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String MP3_PATH = Environment.getExternalStorageDirectory().getPath() + "/Music/";
    ArrayList<DataList> list = new ArrayList<>();
    MyAdapter adapter;
    LinearLayoutManager manager;
    MediaPlayer mediaPlayer=new MediaPlayer();;
    int pause = 0;
    RecyclerView recycler;
    Button btnplay, btnpause, btnstop, btnadd, btndb;
    ProgressBar progress;
    static int po = 0;
    static MyDB mydb;
    static SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        btnplay = findViewById(R.id.btnplay);
        btnpause = findViewById(R.id.btnpause);
        btnstop = findViewById(R.id.btnstop);
        btnadd = findViewById(R.id.btnadd);
        btndb = findViewById(R.id.btndb);
        progress = findViewById(R.id.progress);
        mydb=new MyDB(this);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        File[] files = new File(MP3_PATH).listFiles();
        for (File x : files) {
            String fileName = x.getName();
            if (fileName.length() >= 5) {
                String extandName = fileName.substring(fileName.length() - 3);
                if (extandName.equals("mp3")) {
                        list.add(new DataList(fileName));
                }
            }
        }
        adapter = new MyAdapter(R.layout.item_list, list);
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        btnplay.setOnClickListener(this);
        btnpause.setOnClickListener(this);
        btnstop.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        btndb.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //뮤직플레이어 재생
            case R.id.btnplay:
                try {
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                    }
                    mediaPlayer.setDataSource(MP3_PATH + list.get(po).getName());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    btnplay.setClickable(false);
                    btnpause.setClickable(true);
                    btnstop.setClickable(true);

                    Thread thread = new Thread() {

                        @Override
                        public void run() {
                            if (mediaPlayer == null) {
                                return;
                            }
                            //1. 노래의 총 걸리는 시간
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setMax(mediaPlayer.getDuration());
                                }
                            });

                            while (mediaPlayer.isPlaying()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                });//스레드 안에서 화면위젯을 변경할수 있는 스레드 이다.
                                SystemClock.sleep(100);
                            }
                        }
                    };
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            //재생중에 일시정지와 이어듣기 기능
            case R.id.btnpause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    pause = mediaPlayer.getCurrentPosition();
                    btnpause.setText("이어듣기");
                } else {
                    mediaPlayer.seekTo(pause);
                    mediaPlayer.start();
                    btnpause.setText("중지");
                    pause = 0;
                    Thread thread = new Thread() {

                        @Override
                        public void run() {
                            if (mediaPlayer == null) {
                                return;
                            }
                            //1. 노래의 총 걸리는 시간
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setMax(mediaPlayer.getDuration());
                                }
                            });

                            while (mediaPlayer.isPlaying()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                });//스레드 안에서 화면위젯을 변경할수 있는 스레드 이다.
                                SystemClock.sleep(100);
                            }
                        }
                    };
                    thread.start();
                }
                break;
            //중지
            case R.id.btnstop:
                mediaPlayer.stop();
                mediaPlayer.reset();
                btnplay.setClickable(true);
                btnpause.setClickable(true);
                btnstop.setClickable(false);
                progress.setProgress(0);
                break;
            //데이터 베이스에 저장할 창을 불러 오는 기능
            case R.id.btnadd:
                intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("name", list.get(po).getName());
                break;
            //데이터 베이스에 저장되어 있는 목록창을 가져오는 기능
            case R.id.btndb:
                intent = new Intent(MainActivity.this, DataBase.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
