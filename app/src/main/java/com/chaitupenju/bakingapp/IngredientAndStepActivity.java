package com.chaitupenju.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chaitupenju.bakingapp.fragments.IngredientFragment;
import com.chaitupenju.bakingapp.fragments.StepsFragment;
import com.chaitupenju.bakingapp.pojos.Ingredient;
import com.chaitupenju.bakingapp.pojos.Step;

import java.util.ArrayList;

public class IngredientAndStepActivity extends AppCompatActivity {
    ArrayList<Step> stepsArray;
    ArrayList<Ingredient> ingrArray;
    public static final String INGREDIENT_SEND_LIST = "INGRARRAY";
    public static final String RECIPE_SHARED_PREF = "recipepref";
    public static final String RECIPE_PREF_KEY = "recipekey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_and_step);
        if (getIntent().getExtras() != null) {
            String recipeName = getIntent().getStringExtra("RECIPE");
            this.setTitle(recipeName);
            stepsArray = new ArrayList<>(getIntent().<Step>getParcelableArrayListExtra("STEP"));
            ingrArray = new ArrayList<>(getIntent().<Ingredient>getParcelableArrayListExtra("INGREDIENT"));
        }
        Bundle b1 = new Bundle();
        b1.putParcelableArrayList(INGREDIENT_SEND_LIST, ingrArray);
        Bundle b2 = new Bundle();
        b2.putParcelableArrayList(Constants.STEP_ARRAY_KEY, stepsArray);
        if(savedInstanceState == null) {
            FragmentManager allFragmentManager = getSupportFragmentManager();
            Fragment ingrFragment = new IngredientFragment();
            ingrFragment.setArguments(b1);
            Fragment stepsFragment = new StepsFragment();
            stepsFragment.setArguments(b2);
            allFragmentManager.beginTransaction().replace(R.id.ingredient_fragment, ingrFragment).commit();
            allFragmentManager.beginTransaction().replace(R.id.steps_fragment, stepsFragment).commit();
        }
    }
}
