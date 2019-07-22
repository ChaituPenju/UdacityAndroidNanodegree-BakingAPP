package com.chaitupenju.bakingapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;


public class Ingredient implements Parcelable {
    private float quantity;
    private String measure;
    private String ingredient;
    private static final String delimitStr = "-IS-";
    private static final String delimitIngredientStr = "-IOBJ-";
    public Ingredient(){

    }

    public Ingredient(float quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    protected Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static String arrayToString(ArrayList<Ingredient> ingrs){
        StringBuilder res = new StringBuilder();
        try {
            for (int i = 0; i < ingrs.size(); i++) {
                res.append(ingrs.get(i).quantity).append(delimitIngredientStr).append(ingrs.get(i).measure).append(delimitIngredientStr).append(ingrs.get(i).ingredient);
                if (i < ingrs.size() - 1) {
                    res.append(delimitStr);
                }
            }
        }
        catch(NullPointerException e){
            return "";
        }
        return res.toString();
    }

    public static ArrayList<Ingredient> stringToArray(String string){
        String[] elements = string.split(delimitStr);
        ArrayList<Ingredient> res = new ArrayList<>();
        for (String element : elements) {
            String[] item = element.split(delimitIngredientStr);
            try{
                res.add(new Ingredient(Float.parseFloat(item[0]), item[1], item[2]));
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
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    //@SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}