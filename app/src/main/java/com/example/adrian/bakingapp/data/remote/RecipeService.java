package com.example.adrian.bakingapp.data.remote;

import com.example.adrian.bakingapp.data.model.Recipe;
import com.example.adrian.bakingapp.data.model.RecipeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("baking.json")
//    Call<RecipeResponse> getRecipies();
    Call<List<Recipe>> getRecipies();
}
