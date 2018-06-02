package com.example.adrian.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.adrian.bakingapp.adapter.RecipeAdapter;
import com.example.adrian.bakingapp.data.model.Recipe;
import com.example.adrian.bakingapp.data.model.RecipeResponse;
import com.example.adrian.bakingapp.data.remote.ApiUtils;
import com.example.adrian.bakingapp.data.remote.RecipeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecipeService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getRecipeService();
        mRecyclerView = findViewById(R.id.rv_recipies);
        mAdapter = new RecipeAdapter(this, new ArrayList<Recipe>(0),
            new RecipeAdapter.RecipeItemListener() {
            @Override
            public void onRecipeClick(long id) {
                Toast.makeText(MainActivity.this, "Recipe: " + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        loadRecipies();
    }

    public void loadRecipies() {
        mService.getRecipies().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.e("KIl",response.code() +"");
                if (response.isSuccessful()){
                    List<Recipe> responseList = new ArrayList<Recipe>();
                    responseList.addAll(response.body());
                    Log.e("JSON", response.body().toString());
                    mAdapter.updateRecepies(responseList);
                }else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
//            @Override
//            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
//                if(response.isSuccessful()){
//                    mAdapter.updateRecepies(response.body().getResults());
//                }else {
//                    int statusCode = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecipeResponse> call, Throwable t) {
//
//            }


//            @Override
//            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
//                if (response.isSuccessful()){
////                    mAdapter.updateRecepies(response);
//                    mAdapter.updateRecepies(response.body());
//                }else {
//                    int statusCode  = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Recipe> call, Throwable t) {
//                Log.d("MainActivity", "error loading from API");
//            }

//            @Override
//            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
//
//                if(response.isSuccessful()) {
//                    mAdapter.updateAnswers(response.body().getItems());
//                    Log.d("MainActivity", "posts loaded from API");
//                }else {
//                    int statusCode  = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
//                showErrorMessage();
//                Log.d("MainActivity", "error loading from API");
//
//            }
        });
    }
}
