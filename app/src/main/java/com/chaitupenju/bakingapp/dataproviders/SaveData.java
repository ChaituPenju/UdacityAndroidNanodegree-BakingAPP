package com.chaitupenju.bakingapp.dataproviders;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.chaitupenju.bakingapp.pojos.Ingredient;
import com.chaitupenju.bakingapp.pojos.Recipe;
import com.chaitupenju.bakingapp.pojos.Step;

import java.util.ArrayList;

public class SaveData {
    public static void persistData(final Context context, final ArrayList<Recipe> recipes){
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                for(int i = 0; i < recipes.size(); i++) {
                    ContentValues recipeValues = new ContentValues();
                    recipeValues.put(RecipeColumns.NAME, recipes.get(i).getName());
                    recipeValues.put(RecipeColumns.INGREDIENTS, Ingredient.arrayToString(recipes.get(i).getIngredients()));
                    recipeValues.put(RecipeColumns.STEPS, Step.arrayToString(recipes.get(i).getSteps()));
                    recipeValues.put(RecipeColumns.SERVINGS, recipes.get(i).getServings());
                    context.getContentResolver().insert(RecipeProvider.RecipeItems.CONTENT_URI, recipeValues);
                }
                return null;
            }
        }.execute();
    }
}

