package com.chaitupenju.bakingapp;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chaitupenju.bakingapp.databinding.ActivityVideoDescriptionBinding;
import com.chaitupenju.bakingapp.fragments.VideoDescriptionFragment;
import com.chaitupenju.bakingapp.pojos.Step;

import java.util.ArrayList;

import static com.chaitupenju.bakingapp.Constants.COUNT_KEY;
import static com.chaitupenju.bakingapp.Constants.STEP_ARRAY_KEY;
import static com.chaitupenju.bakingapp.Constants.STEP_OBJECT;

public class VideoDescriptionActivity extends AppCompatActivity {
    Step stepObj;
    ArrayList<Step> stepArrayList;
    public int count;
    VideoDescriptionFragment vdFragment;
    ActivityVideoDescriptionBinding mVideoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_description);
        if (isLandscape()) {
            getSupportActionBar().hide();
        }else{
            getSupportActionBar().show();
        }
        mVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_description);
        stepArrayList = getIntent().getParcelableArrayListExtra(STEP_ARRAY_KEY);
        stepObj = getIntent().getParcelableExtra(STEP_OBJECT);
        if(savedInstanceState != null){
            count = savedInstanceState.getInt(COUNT_KEY);
        }
        else{
            count = stepObj.getId();
            Log.e("XYZ","saved instance state is null");
            updateFragment(count);
        }
        mVideoBinding.tvCount.setText(String.valueOf(count));
        if(count == 0) mVideoBinding.btnPrevious.setEnabled(false);
        if(count == stepArrayList.size() - 1) mVideoBinding.btnNext.setEnabled(false);
    }

    private boolean isLandscape(){
        return getResources().getBoolean(R.bool.is_landscape);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNT_KEY, count);
    }

    private void updateFragment(int count){
        Step steps = new Step();
        steps.setId(stepArrayList.get(count).getId());
        steps.setDescription(stepArrayList.get(count).getDescription());
        steps.setVideoURL(stepArrayList.get(count).getVideoURL());
        steps.setThumbnailURL(stepArrayList.get(count).getThumbnailURL());
        vdFragment = VideoDescriptionFragment.newInstance(steps);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.video_description_fragment, vdFragment).commit();
        mVideoBinding.tvCount.setText(String.valueOf(count));
    }

    public void buttonPreviousClick(View view) {
        if(!mVideoBinding.btnNext.isEnabled()) mVideoBinding.btnNext.setEnabled(true);
        if(count == 1)
            mVideoBinding.btnPrevious.setEnabled(false);
        count --;
        updateFragment(count);
    }

    public void buttonNextClick(View view) {
        if(!mVideoBinding.btnPrevious.isEnabled()) mVideoBinding.btnPrevious.setEnabled(true);
        if(count == stepArrayList.size() - 2)
            mVideoBinding.btnNext.setEnabled(false);
        count ++;
        updateFragment(count);
    }

}
