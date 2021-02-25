package com.nulll.exoplayerapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.nulll.exoplayerapplication.Classes.Config;
import com.nulll.exoplayerapplication.R;


public class SubtitledVidsActivity extends AppCompatActivity {


    private SimpleExoPlayer player, player2;
    private PlayerView simpleExoPlayerView, simpleExoPlayerView2;
    LottieAnimationView lottieAnimationView, lottieAnimationView2;
    ImageView playBtn, playBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtitled_vids);

        initViews();


        setFirstItem();
        setSecondItem();

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


    private void setFirstItem() {
        player = new SimpleExoPlayer.Builder(this).build();
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        /**
         *  we'll create two different media sources:
         //@MediaSource contentMediaSource, which is the source for our video
         //@SingleSampleMediaSource subtitleSource content, which is the source for our subtitles
         // then we merge the sources useing  @MergingMediaSource() so that the player shows the video with subtitle synchronized
         */
        MediaSource contentMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Config.courseraVideo));
        MediaSource[] mediaSources = new MediaSource[2]; //The Size must change depending on the Uris
        mediaSources[0] = contentMediaSource; // uri


        Format format = Format.createTextSampleFormat(
                null, // An identifier for the track. May be null.
                MimeTypes.TEXT_VTT, // The mime type. Must be set correctly.
                Format.NO_VALUE, // Selection flags for the track.
                "en"); // The subtitle language. May be null.

        SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource.Factory(dataSourceFactory).
                createMediaSource(Uri.parse(Config.courseraSubtitle), format, C.TIME_UNSET);


        mediaSources[1] = subtitleSource;

        MediaSource mediaSource = new MergingMediaSource(mediaSources);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        simpleExoPlayerView.setPlayer(player);
        player.addListener(new Player.EventListener() {
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

    private void setSecondItem() {
        player2 = new SimpleExoPlayer.Builder(this).build();
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();

        /**
         *  we'll create two different media sources:
         //@MediaSource contentMediaSource, which is the source for our video
         //@SingleSampleMediaSource subtitleSource content, which is the source for our subtitles
         // then we merge the sources useing  @MergingMediaSource() so that the player shows the video with subtitle synchronized
         */

        //content media source
        MediaSource contentMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Config.courseraVideo2));
        MediaSource[] mediaSources = new MediaSource[2]; //The Size must change depending on the Uris
        mediaSources[0] = contentMediaSource; // uri


        Format format = Format.createTextSampleFormat(
                null, // An identifier for the track. May be null.
                MimeTypes.TEXT_VTT, // The mime type. Must be set correctly.
                Format.NO_VALUE, // Selection flags for the track.
                "en"); // The subtitle language. May be null.

        //subtitle media source
        SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource.Factory(dataSourceFactory).
                createMediaSource(Uri.parse(Config.courseraSubtitle2), format, C.TIME_UNSET);


        mediaSources[1] = subtitleSource;
        MediaSource mediaSource = new MergingMediaSource(mediaSources);


        player2.prepare(mediaSource);
        player2.setPlayWhenReady(false);
        simpleExoPlayerView2.setPlayer(player2);
        player2.addListener(new Player.EventListener() {
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

    //init view
    private void initViews() {
        lottieAnimationView = findViewById(R.id.animation_dash_loading4);
        lottieAnimationView2 = findViewById(R.id.animation_dash_loading5);
        simpleExoPlayerView = findViewById(R.id.player_view4);
        simpleExoPlayerView2 = findViewById(R.id.player_view5);
        playBtn = simpleExoPlayerView.findViewById(R.id.exo_play);
        playBtn2 = simpleExoPlayerView2.findViewById(R.id.exo_play);


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
        simpleExoPlayerView.setPlayer(null);
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