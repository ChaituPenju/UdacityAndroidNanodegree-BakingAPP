package com.chaitupenju.bakingapp.networkutils;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chaitupenju.bakingapp.IngredientAndStepActivity.RECIPE_PREF_KEY;
import static com.chaitupenju.bakingapp.IngredientAndStepActivity.RECIPE_SHARED_PREF;

public class CakesJsonDataApi {
    static ConnectivityManager connectivityManager;
    static NetworkInfo wifiInfo, mobileInfo;
    static boolean connected = false;
    private static final String URL = "https://d17h27t6h515a5.cloudfront.net/";

    //public static HttpUrl baseURL = HttpUrl.parse(URL);

    public static Retrofit cakeRetrofit = null;

    public static Retrofit getCakeNames(){
        if (cakeRetrofit == null){
            cakeRetrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return cakeRetrofit;
    }

    public static boolean isOnline(Context context) {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
        return connected;
    }

    public static void saveRecipePref(SharedPreferences prefs, String name){
        SharedPreferences.Editor recipeEdit = prefs.edit();
        recipeEdit.putString(RECIPE_PREF_KEY, name);
        recipeEdit.apply();
    }

    public static String getFavouritePref(Context context){
        SharedPreferences recipePrefs;
        recipePrefs = context.getSharedPreferences(RECIPE_SHARED_PREF, Context.MODE_PRIVATE);
        return recipePrefs.getString(RECIPE_PREF_KEY, "Nutella Pie");
    }
}
