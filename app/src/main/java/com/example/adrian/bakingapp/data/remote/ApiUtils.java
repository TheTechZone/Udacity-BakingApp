package com.example.adrian.bakingapp.data.remote;

import com.example.adrian.bakingapp.data.model.Recipe;

public class ApiUtils {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static RecipeService getRecipeService() {
        return RetrofitClient.getClient(BASE_URL).create(RecipeService.class);
    }
}
