package com.chaitupenju.bakingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaitupenju.bakingapp.Constants;
import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.VideoDescriptionActivity;
import com.chaitupenju.bakingapp.adapters.StepsListBaseAdapter;
import com.chaitupenju.bakingapp.pojos.Step;

import java.util.ArrayList;

import static com.chaitupenju.bakingapp.Constants.STEP_ARRAY_KEY;
import static com.chaitupenju.bakingapp.Constants.STEP_OBJECT;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {

    ArrayList<Step> stepArray;
    StepsListBaseAdapter stepBaseAdapter;
    ListView stepLv;
    public StepsFragment() {
        // Required empty public constructor
        stepArray = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        //stepRv = rootView.findViewById(R.id.rv_step_frag);
        stepLv = rootView.findViewById(R.id.lv_step_frag);
        stepArray = getArguments().getParcelableArrayList(STEP_ARRAY_KEY);
        initStepsFragment(stepArray);
        return rootView;
    }

    private void initStepsFragment(final ArrayList<Step> stepArray) {
        stepBaseAdapter = new StepsListBaseAdapter(getActivity(), stepArray);
        setListViewHeight(stepBaseAdapter, stepLv);
        stepLv.setAdapter(stepBaseAdapter);
        if(stepBaseAdapter != null)
            stepBaseAdapter.notifyDataSetChanged();
        stepLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isTablet = getResources().getBoolean(R.bool.isTablet);
                if(isTablet){
                    Bundle b = new Bundle();
                    b.putParcelable(STEP_OBJECT, stepArray.get(position));
                    VideoDescriptionFragment vdFragment = new VideoDescriptionFragment();
                    vdFragment.setArguments(b);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.video_description_fragment, vdFragment).commit();
                }
                else{
                    Intent in = new Intent(getContext(), VideoDescriptionActivity.class);
                    in.putParcelableArrayListExtra(STEP_ARRAY_KEY, stepArray);
                    in.putExtra(Constants.STEP_OBJECT, stepArray.get(position));
                    startActivity(in);
                }
            }
        });
    }


    private void setListViewHeight(StepsListBaseAdapter stepsBaseAdapter, ListView listView){
        int totalHeight = 0;
        for (int i = 0; i < stepsBaseAdapter.getCount(); i++){
            View stepListItem = stepsBaseAdapter.getView(i, null, listView);
            stepListItem.measure(0,0);
            totalHeight += stepListItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams stepParams = listView.getLayoutParams();
        stepParams.height = totalHeight + (listView.getDividerHeight() * (stepsBaseAdapter.getCount()));
        listView.setLayoutParams(stepParams);
        listView.requestLayout();
    }
}
