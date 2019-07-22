package com.chaitupenju.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.pojos.Ingredient;

import java.util.ArrayList;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder>{

    private Context context;
    private ArrayList<Ingredient> ingredientArrayList;
    public IngredientListAdapter(Context context, ArrayList<Ingredient> ingredientArrayList){
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @NonNull
    @Override
    public IngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_items_list, parent, false);
        return new IngredientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListViewHolder holder, int position) {
        Ingredient ingrItem = ingredientArrayList.get(position);
        holder.count.setText(""+(position + 1));
        holder.ingredientName.setText(ingrItem.getIngredient());
        String quanMeasTemp = ingrItem.getQuantity() + "  " + ingrItem.getMeasure();
        holder.quantNdMeas.setText(quanMeasTemp);
    }

    @Override
    public int getItemCount() {
        if (ingredientArrayList.size() == 0) return 0;
        return ingredientArrayList.size();
    }

    class IngredientListViewHolder extends RecyclerView.ViewHolder{
        TextView count, ingredientName, quantNdMeas;
        public IngredientListViewHolder(View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.tv_ingr_count);
            ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
            quantNdMeas = itemView.findViewById(R.id.tv_quanmeas_name);
        }
    }
}
