package com.chaitupenju.bakingapp.networkutils;

import com.chaitupenju.bakingapp.pojos.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRecipesInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getCakesList();
}
