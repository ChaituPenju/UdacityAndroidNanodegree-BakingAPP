package com.chaitupenju.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.adapters.IngredientListAdapter;
import com.chaitupenju.bakingapp.pojos.Ingredient;

import java.util.ArrayList;

import static com.chaitupenju.bakingapp.IngredientAndStepActivity.INGREDIENT_SEND_LIST;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {
    ArrayList<Ingredient> ingrArray;
    IngredientListAdapter ingrAdapter;
    RecyclerView ingrRv;
    public IngredientFragment() {
        // Required empty public constructor
        ingrArray = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ingrRv = rootView.findViewById(R.id.rv_ingr_frag);
        ingrArray = getArguments().getParcelableArrayList(INGREDIENT_SEND_LIST);
        initIngredientFragment(ingrArray);
        return rootView;
    }

    private void initIngredientFragment(ArrayList<Ingredient> ingrArray) {
        ingrAdapter = new IngredientListAdapter(getContext(), ingrArray);
            ingrAdapter.notifyDataSetChanged();
        ingrRv.setLayoutManager(new LinearLayoutManager(getContext()));
        ingrRv.setHasFixedSize(true);
        ingrRv.setAdapter(ingrAdapter);
    }

}
