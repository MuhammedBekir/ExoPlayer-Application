package com.nulll.exoplayerapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nulll.exoplayerapplication.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView hlsCard, dashCard, subtitledCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        hlsCard = findViewById(R.id.hls_videos_card);
        dashCard = findViewById(R.id.dash_videos_card);
        dashCard = findViewById(R.id.dash_videos_card);
        subtitledCard = findViewById(R.id.subtitled_videos_card);

        hlsCard.setOnClickListener(this);
        dashCard.setOnClickListener(this);
        subtitledCard.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.hls_videos_card:
                Intent intent = new Intent(MainActivity.this, HlsVidActivity.class);
                startActivity(intent);
                break;
            case R.id.dash_videos_card:

                Intent intent2 = new Intent(MainActivity.this, DashVidActivity.class);
                startActivity(intent2);
                break;
            case R.id.subtitled_videos_card:
                Intent intent3 = new Intent(MainActivity.this, SubtitledVidsActivity.class);
                startActivity(intent3);
                break;
        }
    }



}