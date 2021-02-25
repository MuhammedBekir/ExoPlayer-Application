package com.nulll.exoplayerapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.nulll.exoplayerapplication.Classes.Config;
import com.nulll.exoplayerapplication.R;

public class HlsVidActivity extends AppCompatActivity {

    private SimpleExoPlayer player, player2;
    private PlayerView simpleExoPlayerView, simpleExoPlayerView2;
    LottieAnimationView lottieAnimationView, lottieAnimationView2;
    ImageView subtitleBtn, subtitleBtn2, playBtn, playBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hls_vid);

        initViews();


        player = new SimpleExoPlayer.Builder(this).build();
        player2 = new SimpleExoPlayer.Builder(this).build();


        HlsItem();
        HlsItem2();
        subtitleBtn.setOnClickListener(view -> {
            Toast.makeText(this, R.string.noTranslations, Toast.LENGTH_LONG).show();

        });
        subtitleBtn2.setOnClickListener(view -> {
            Toast.makeText(this, R.string.noTranslations, Toast.LENGTH_LONG).show();
        });

        playBtn.setOnClickListener(view -> {
            if (player2.isPlaying()) {
                player2.pause();

            }
            player.play();

        });
        playBtn2.setOnClickListener(view -> {
            if (player.isPlaying()) {
                player.pause();
            }
            player2.play();
        });
    }

    private void initViews() {
        lottieAnimationView = findViewById(R.id.animation_loading);
        lottieAnimationView2 = findViewById(R.id.animation_loading2);
        simpleExoPlayerView = findViewById(R.id.player_view);
        simpleExoPlayerView2 = findViewById(R.id.player_view2);
        subtitleBtn = simpleExoPlayerView.findViewById(R.id.subtitle_img);
        subtitleBtn2 = simpleExoPlayerView2.findViewById(R.id.subtitle_img2);
        playBtn = simpleExoPlayerView.findViewById(R.id.exo_play);
        playBtn2 = simpleExoPlayerView2.findViewById(R.id.exo_play);


    }

    private void HlsItem() {
        DataSource.Factory factory = new DefaultHttpDataSource.Factory();
        MediaSource mediaSource = new HlsMediaSource.Factory(factory).createMediaSource(Uri.parse(Config.hlsVide));


        player.setMediaSource(mediaSource);

        simpleExoPlayerView.setPlayer(player);
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(@NonNull Timeline timeline, int reason) {

            }

            @Override
            public void onPlaybackStateChanged(int state) {
                switch (state) {
                    case Player.STATE_BUFFERING:
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:
                        lottieAnimationView.setVisibility(View.GONE);
                        break;


                }

            }
        });


    }

    private void HlsItem2() {
        DataSource.Factory factory = new DefaultHttpDataSource.Factory();
        MediaSource mediaSource = new HlsMediaSource.Factory(factory).createMediaSource(Uri.parse(Config.hlsVide2));


        player2.setMediaSource(mediaSource);

        simpleExoPlayerView2.setPlayer(player2);
        // Prepare the player.
        player2.prepare();
        player2.setPlayWhenReady(false);
        player2.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(@NonNull Timeline timeline, int reason) {

            }

            @Override
            public void onPlaybackStateChanged(int state) {
                switch (state) {
                    case Player.STATE_BUFFERING:
                        lottieAnimationView2.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:
                        lottieAnimationView2.setVisibility(View.GONE);
                        break;


                }

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

        simpleExoPlayerView.setPlayer(null);
        simpleExoPlayerView2.setPlayer(null);
        if (player != null && player2 != null) {
            player.release();
            player2.release();
            player = null;
            player2 = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player2 != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
            player = null;

            player2.setPlayWhenReady(false);
            player2.stop();
            player2.release();
            player2 = null;
        }
    }

}