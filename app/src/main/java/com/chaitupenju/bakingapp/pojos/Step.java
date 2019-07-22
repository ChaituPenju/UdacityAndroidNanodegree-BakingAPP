package com.chaitupenju.bakingapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class Step implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private static final String delimitStr = "-SS-";
    private static final String delimitStepStr = "-SOBJ-";

    public Step(int id, String shortDescription, String description) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
    }

    private String thumbnailURL;

    public Step (){
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static String arrayToString(ArrayList<Step> steps){
        StringBuilder res = new StringBuilder();
        try {
            for (int i = 0; i < steps.size(); i++) {
                res.append(steps.get(i).id).append(delimitStepStr).append(steps.get(i).shortDescription).append(delimitStepStr).append(steps.get(i).description);
                if (i < steps.size() - 1) {
                    res.append(delimitStr);
                }
            }
        }
        catch(NullPointerException e){
            return "";
        }
        return res.toString();
    }

    public static ArrayList<Step> stringToArray(String string){
        String[] elements = string.split(delimitStr);
        ArrayList<Step> res = new ArrayList<>();
        for (String element : elements) {
            String[] item = element.split(delimitStepStr);
            try{
                res.add(new Step(Integer.parseInt(item[0]), item[1], item[2]));
            }catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        Log.i("STR2ARR",res.toString());
        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}