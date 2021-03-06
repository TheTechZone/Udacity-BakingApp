package com.example.adrian.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.adapter.RecipeAdapter;
import com.example.adrian.bakingapp.data.model.Recipe;
import com.example.adrian.bakingapp.data.remote.ApiUtils;
import com.example.adrian.bakingapp.data.remote.RecipeService;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecipeService mService;
    private CountingIdlingResource idlingResource = new CountingIdlingResource("MAIN_ACTIVITY_LOADER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getRecipeService();
        mRecyclerView = findViewById(R.id.rv_recipies);
        mAdapter = new RecipeAdapter(this, new ArrayList<Recipe>(0),
                new RecipeAdapter.RecipeItemListener() {
                    @Override
                    public void onRecipeClick(long id, Recipe recipe) {
                        Intent intent = new Intent(MainActivity.this, StepListActivity.class);
                        Parcelable wrapped = Parcels.wrap(recipe);
                        intent.putExtra(StepListActivity.RECIPE_BUNDLE_KEY, wrapped);
                        startActivity(intent);
                    }
                });

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        int spanCount = 1;
        if (tabletSize) {
            spanCount = 2;
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        IdlingRegistry.getInstance().register(idlingResource);
        idlingResource.increment();
        loadRecipies();
    }

    public void loadRecipies() {
        mService.getRecipies().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> responseList = new ArrayList<Recipe>();
                    responseList.addAll(response.body());
                    mAdapter.updateRecepies(responseList);
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
                idlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }
}
