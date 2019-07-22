package com.chaitupenju.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.pojos.Step;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.chaitupenju.bakingapp.Constants.STEP_OBJECT;

public class VideoDescriptionFragment extends Fragment {
    SimpleExoPlayerView mPlayerView;
    SimpleExoPlayer mExoPlayer;
    ImageView noVideo;
    Step stepObj;
    TextView shortDesc;
    String url;
    String descStr;
    String thumbnailUrl;
    boolean setPlayer;
    long playerPos;
    View rootView;

    public VideoDescriptionFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_video_description, container, false);
        noVideo = rootView.findViewById(R.id.iv_no_video);
        shortDesc = rootView.findViewById(R.id.tv_short_desc);
        mPlayerView = rootView.findViewById(R.id.exo_player_view);
        stepObj = getArguments().getParcelable(STEP_OBJECT);
        if (stepObj != null) {
            url = stepObj.getVideoURL();
            thumbnailUrl = stepObj.getThumbnailURL();
            descStr = stepObj.getDescription();
        }
        if (savedInstanceState != null) {
            playerPos = savedInstanceState.getLong("PP");
            setPlayer = savedInstanceState.getBoolean("PS");
            if (mExoPlayer == null) {
                initializeplayer(url);
            }
            mExoPlayer.seekTo(playerPos);
            mExoPlayer.setPlayWhenReady(setPlayer);
        }else{
                if (!thumbnailUrl.equals("")) {
                    Uri builtUri = Uri.parse(thumbnailUrl).buildUpon().build();
                    Glide.with(this).load(builtUri).into(noVideo);
                } else {
                    setVideoOrImage(url);
                }
    }
        shortDesc.setText(descStr);
        return rootView;
 }

    public static VideoDescriptionFragment newInstance(Step stepObj) {
        VideoDescriptionFragment vdFrag = new VideoDescriptionFragment();
        Bundle fragBundle = new Bundle();
        fragBundle.putParcelable(STEP_OBJECT, stepObj);
        vdFrag.setArguments(fragBundle);
        return vdFrag;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        playerPos = mExoPlayer.getCurrentPosition();
        setPlayer = mExoPlayer.getPlayWhenReady();
        outState.putLong("PP", playerPos);
        outState.putBoolean("PS", setPlayer);
        super.onSaveInstanceState(outState);
    }

    private void setPlayerInvisible(){mPlayerView.setVisibility(View.INVISIBLE);
    mExoPlayer = null;
    noVideo.setVisibility(View.VISIBLE);
    }

    private void setVideoOrImage(String url){
        if(url.equals("")){
            setPlayerInvisible();
        }
        else{
            setPlayerVisible();
        }
    }
    private void setPlayerVisible(){mPlayerView.setVisibility(View.VISIBLE);
    noVideo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeplayer(url);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializeplayer(url);
        }
    }


    public void initializeplayer(String uri) {
        Uri mediaUri = Uri.parse(uri);
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "RecipeBakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        playerPos = mExoPlayer.getCurrentPosition();
        setPlayer = mExoPlayer.getPlayWhenReady();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
