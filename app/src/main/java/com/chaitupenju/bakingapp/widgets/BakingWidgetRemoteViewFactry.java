package com.chaitupenju.bakingapp.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chaitupenju.bakingapp.Constants;
import com.chaitupenju.bakingapp.R;

import com.chaitupenju.bakingapp.networkutils.CakesJsonDataApi;
import com.chaitupenju.bakingapp.pojos.Ingredient;

import java.util.ArrayList;

public class BakingWidgetRemoteViewFactry implements RemoteViewsService.RemoteViewsFactory {

    private Context wContext;
    private String favRecipeName;
    private ArrayList<Ingredient> ingredientArrayList;

    public BakingWidgetRemoteViewFactry(Context context, Intent intent){
        this.wContext = context;
        ingredientArrayList = new ArrayList<>();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        long idToken = Binder.clearCallingIdentity();
        favRecipeName = CakesJsonDataApi.getFavouritePref(wContext);
        String ingredientStr = Constants.getIngredientDataString(wContext, favRecipeName);
        ingredientArrayList = Ingredient.stringToArray(ingredientStr);
        /*Ingredient test = new Ingredient(4.4f, "measure", "quantity");
        ingredientArrayList.add(test);*/
        Binder.restoreCallingIdentity(idToken);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredientArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews wRemoteViews = new RemoteViews(wContext.getPackageName(), R.layout.ingredient_widget_items_list);
        wRemoteViews.setTextViewText(R.id.tv_wingr_count, String.valueOf(position));
        Ingredient ingrObj = ingredientArrayList.get(position);
        wRemoteViews.setTextViewText(R.id.tv_wingredient_name, ingrObj.getIngredient());
        String quannMeas = String.valueOf(ingrObj.getQuantity()) + " " + ingrObj.getMeasure();
        wRemoteViews.setTextViewText(R.id.tv_wquanmeas_name, quannMeas);


        Intent fillInIntent = new Intent();
        wRemoteViews.setOnClickFillInIntent(R.id.lv_widget_container, fillInIntent);


        return wRemoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
