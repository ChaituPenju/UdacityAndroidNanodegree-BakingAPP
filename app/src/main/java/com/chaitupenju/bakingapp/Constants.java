package com.chaitupenju.bakingapp;

import android.content.Context;
import android.database.Cursor;

import com.chaitupenju.bakingapp.dataproviders.RecipeColumns;
import com.chaitupenju.bakingapp.dataproviders.RecipeProvider;

public class Constants {
    public static final String STEP_ARRAY_KEY = "STEPARRAY";
    public static final String STEP_OBJECT = "OBJECT";
    public static final String COUNT_KEY = "COUNT";
    public static final String SCROLL_POS_KEY = "scroll-position";
    public static final String ACTION_UPDATE_WIDGET = "com.chaitupenju.bakingapp.action.update_widget";
    public static final String ONSAVE_INGREDIENT_ARRAY_KEY = "INGREDIENT-IASA";
    public static final String ONSAVE_STEP_ARRAY_KEY = "STEP-IASA";

    public static String getIngredientDataString(Context context, String recipeName){
        String selection = RecipeColumns.NAME + " LIKE ?";
        String ret = null;
        String[] selectionArgs = {recipeName};
        Cursor wCursor;
        wCursor = context.getContentResolver().query(RecipeProvider.RecipeItems.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        if(wCursor.moveToFirst()){
            ret = wCursor.getString(wCursor.getColumnIndex(RecipeColumns.INGREDIENTS));
        }
        return ret;
    }
}
