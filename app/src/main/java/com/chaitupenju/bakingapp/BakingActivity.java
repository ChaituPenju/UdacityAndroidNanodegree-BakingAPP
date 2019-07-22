package com.chaitupenju.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaitupenju.bakingapp.dataproviders.SaveData;
import com.chaitupenju.bakingapp.networkutils.CakesJsonDataApi;
import com.chaitupenju.bakingapp.networkutils.GetRecipesInterface;
import com.chaitupenju.bakingapp.adapters.CakeRecipeListAdapter;
import com.chaitupenju.bakingapp.databinding.ActivityBakingBinding;
import com.chaitupenju.bakingapp.dataproviders.RecipeColumns;
import com.chaitupenju.bakingapp.dataproviders.RecipeProvider;
import com.chaitupenju.bakingapp.pojos.Recipe;
import com.chaitupenju.bakingapp.widgets.BakingAppWidget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chaitupenju.bakingapp.Constants.SCROLL_POS_KEY;
import static com.chaitupenju.bakingapp.IngredientAndStepActivity.RECIPE_PREF_KEY;
import static com.chaitupenju.bakingapp.IngredientAndStepActivity.RECIPE_SHARED_PREF;

public class BakingActivity extends AppCompatActivity {
    ActivityBakingBinding mBinding;
    CakeRecipeListAdapter recipeAdapter;
    ArrayList<Recipe> recipeArrayList;
    SharedPreferences recipePrefs;
    Parcelable mListState;
    LinearLayoutManager mLayoutManager;
    @Nullable BakingIdlingResource mIdlingResource;


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if (mIdlingResource == null) {
            mIdlingResource = new BakingIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);
        recipePrefs = getSharedPreferences(RECIPE_SHARED_PREF, Context.MODE_PRIVATE);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_baking);
        mBinding.progressBar.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(this);
        if(recipePrefs.contains(RECIPE_PREF_KEY)){
            Toast.makeText(this, "Favourite Recipe"+recipePrefs.getString(RECIPE_PREF_KEY, "Nutella Pie"), Toast.LENGTH_SHORT).show();
        }
        getIdlingResource();
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
         }
        if(CakesJsonDataApi.isOnline(getApplicationContext()))
            getData();
        else{
            TextView errorTv;
            errorTv = findViewById(R.id.tv_error_msg);
            errorTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(SCROLL_POS_KEY, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(SCROLL_POS_KEY);
    }

    private void getData(){
        GetRecipesInterface cakeData = CakesJsonDataApi.getCakeNames().create(GetRecipesInterface.class);
        final Call<List<Recipe>> cakeList = cakeData.getCakesList();
        cakeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()) {
                    recipeArrayList = new ArrayList<>(response.body());
                    initRecipeList(recipeArrayList);
                    if (mIdlingResource != null) {
                        mIdlingResource.setIdleState(true);
                    }
                    if(isDataPersisted()){
                        SaveData.persistData(getApplicationContext(), recipeArrayList);
                    }
                    mBinding.progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("ERR", "FAiled to connect");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_favourite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        SharedPreferences.Editor editor = recipePrefs.edit();
        switch(itemId){
            case R.id.item_nutella:
                editor.putString(RECIPE_PREF_KEY, getString(R.string.item_nutella_pie));
                editor.apply();
                Toast.makeText(this, "Nutella Pie set as your Favourite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_brownie:
                editor.putString(RECIPE_PREF_KEY, getString(R.string.item_brownies));
                editor.apply();
                Toast.makeText(this, "Brownies set as your Favourite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_yellow:
                editor.putString(RECIPE_PREF_KEY, getString(R.string.item_yellow_cake));
                editor.apply();
                Toast.makeText(this, "Yellow Cake set as your Favourite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_cheese:
                editor.putString(RECIPE_PREF_KEY, getString(R.string.item_cheesecake));
                editor.apply();
                Toast.makeText(this, "Cheesecake set as your Favourite", Toast.LENGTH_SHORT).show();
                break;
        }
        BakingAppWidget.setServiceBroadcast(getApplicationContext());
        return true;
    }

    private void initRecipeList(ArrayList<Recipe> recipeList){
        recipeAdapter = new CakeRecipeListAdapter(this, recipeList);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            GridLayoutManager glm = new GridLayoutManager(this, 3);
            mBinding.recipeListRecyclerView.setLayoutManager(glm);
        }
        else{
            mBinding.recipeListRecyclerView.setLayoutManager(mLayoutManager);
        }
        mBinding.recipeListRecyclerView.setAdapter(recipeAdapter);
    }

    public boolean isDataPersisted(){
        String favRecipe = CakesJsonDataApi.getFavouritePref(this);
        String selection = RecipeColumns.NAME + " LIKE ?";
        String[] selectionArgs = {favRecipe};
        Cursor cursor = getContentResolver().query(RecipeProvider.RecipeItems.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        if(cursor != null && cursor.getCount()>0){
            return false;
        }else{
            return true;
        }
    }
}
