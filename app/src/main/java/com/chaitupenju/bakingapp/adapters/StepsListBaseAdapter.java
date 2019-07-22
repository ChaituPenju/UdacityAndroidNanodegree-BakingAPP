package com.chaitupenju.bakingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.pojos.Step;

import java.util.ArrayList;

public class StepsListBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Step> stepArrayList;
    public StepsListBaseAdapter(Context context, ArrayList<Step> stepArrayList){
        this.context = context;
        this.stepArrayList = stepArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stepArrayList.size();
    }

    @Override
    public Step getItem(int position) {
        return stepArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StepViewHolder sViewHolder;
        View rootView = convertView;
        if(rootView == null){
            LayoutInflater li;
            li = LayoutInflater.from(parent.getContext());
            rootView = li.inflate(R.layout.steps_items_list, parent, false);
            sViewHolder = new StepViewHolder(rootView);
            rootView.setTag(sViewHolder);
        }
        else{ sViewHolder = (StepViewHolder) rootView.getTag(); }

        Step step = getItem(position);
        sViewHolder.stepId.setText(String.valueOf(step.getId()));
        sViewHolder.stepDesc.setText(step.getShortDescription());
        return rootView;
    }
    private class StepViewHolder{
        TextView stepId, stepDesc;
        StepViewHolder(View view){
            stepId = view.findViewById(R.id.tv_step_id);
            stepDesc = view.findViewById(R.id.tv_step_desc);
        }
    }
}
