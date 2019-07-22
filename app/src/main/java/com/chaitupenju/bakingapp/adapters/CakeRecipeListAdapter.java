package com.chaitupenju.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chaitupenju.bakingapp.IngredientAndStepActivity;
import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.networkutils.CakesJsonDataApi;
import com.chaitupenju.bakingapp.pojos.Ingredient;
import com.chaitupenju.bakingapp.pojos.Recipe;
import com.chaitupenju.bakingapp.pojos.Step;

import java.util.ArrayList;

import static com.chaitupenju.bakingapp.IngredientAndStepActivity.RECIPE_SHARED_PREF;

public class CakeRecipeListAdapter extends RecyclerView.Adapter<CakeRecipeListAdapter.RecipeCakeListViewHolder>{

    Context context;
    ArrayList<Recipe> recipeList;
    SharedPreferences recipePrefs;
    public CakeRecipeListAdapter(Context context, ArrayList<Recipe> recipeList){
        this.context = context;
        this.recipeList = recipeList;
    }


    @NonNull
    @Override
    public RecipeCakeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        recipePrefs = context.getSharedPreferences(RECIPE_SHARED_PREF, Context.MODE_PRIVATE);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.cake_items_list, parent, false);
        return new RecipeCakeListViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCakeListViewHolder holder, final int position) {

        Recipe recipeItem = recipeList.get(position);
        String recipeName = recipeItem.getName();
        holder.recipeName.setText(recipeName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Item Clicked is :"+recipeList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent ingrAndStep = new Intent(context, IngredientAndStepActivity.class);
                ArrayList<Step> stepArray = new ArrayList<>(recipeList.get(position).getSteps());
                ArrayList<Ingredient> ingredientArray = new ArrayList<>(recipeList.get(position).getIngredients());
                ingrAndStep.putParcelableArrayListExtra("INGREDIENT", ingredientArray);
                ingrAndStep.putParcelableArrayListExtra("STEP", stepArray);
                ingrAndStep.putExtra("RECIPE", recipeList.get(position).getName());
                context.startActivity(ingrAndStep);
                CakesJsonDataApi.saveRecipePref(recipePrefs, recipeList.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipeList.size() == 0)
           return 0;
        return recipeList.size();
    }

    public class RecipeCakeListViewHolder extends RecyclerView.ViewHolder{
    TextView recipeName;
    public RecipeCakeListViewHolder(View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.tv_cake_name);
    }
}
}
