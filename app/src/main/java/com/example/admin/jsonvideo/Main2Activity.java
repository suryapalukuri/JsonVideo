package com.example.admin.jsonvideo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURI;
    RecyclerView recyclerView;
    //List<Data> data=new ArrayList<>();
    Context context;
    TextView description,title;
    String desc,titl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player_view);
        videoURI = getIntent().getStringExtra("video");
        description = (TextView) findViewById(R.id.description1);
        desc = getIntent().getStringExtra("des");
        title = (TextView) findViewById(R.id.title1);
        titl = getIntent().getStringExtra("tit");
        exoPlayerView=findViewById(R.id.exo_player_view);
        ArrayList<Data> data = new ArrayList<Data>();
        data = (ArrayList<Data>) getIntent().getSerializableExtra("data");
        Log.i("title and desc", desc + " ## " + titl+"$$"+videoURI);
        Log.i("list ", data.toString());
        title.setText(titl);
        description.setText(desc);


        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURI), dataSourceFactory, extractorsFactory, null, null);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            }catch (Exception e){
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());

        }
        recyclerView=findViewById(R.id.recyclerview1);
        DataAdapter adapter=new DataAdapter(getApplicationContext(),data);
        recyclerView.setAdapter(adapter);
    }
}
