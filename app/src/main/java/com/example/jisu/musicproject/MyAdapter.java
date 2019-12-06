package com.example.jisu.musicproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {
    private int layout;
    private ArrayList<DataList> list=new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

    public MyAdapter(int layout, ArrayList<DataList> list) {
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder customViewHolder, final int i) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String sName = MainActivity.MP3_PATH + list.get(i).getName();
        mmr.setDataSource(sName);
        byte[] data = mmr.getEmbeddedPicture();

        customViewHolder.tvname.setText(list.get(i).getName());

        int value=0;
        try {
            MediaPlayer med = new MediaPlayer();
            med.setDataSource(sName);
            med.prepare();
            value= med.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.get(i).setTime(value);

        customViewHolder.tvtime.setText(simpleDateFormat.format(list.get(i).getTime()));

        if (data != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            customViewHolder.imageview.setImageBitmap(bitmap);
        } else {
            customViewHolder.imageview.setImageResource(R.drawable.music);
        }
        customViewHolder.imageview.setAdjustViewBounds(true);

        customViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.po=i;
            }
        });
    }

    @Override
    public int getItemCount() { return (list != null) ? list.size() : 0; }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView tvname, tvtime;
        LinearLayout layout;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            tvname = itemView.findViewById(R.id.tvname);
            tvtime = itemView.findViewById(R.id.tvtime);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}