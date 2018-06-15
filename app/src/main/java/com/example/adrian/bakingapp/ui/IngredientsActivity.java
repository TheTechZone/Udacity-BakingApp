package com.example.adrian.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.adapter.IngredientsAdapter;
import com.example.adrian.bakingapp.data.model.Recipe;

import org.parceler.Parcels;

public class IngredientsActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        final Intent intent = getIntent();

        mRecipe = Parcels.unwrap(intent.getParcelableExtra(StepListActivity.RECIPE_BUNDLE_KEY));

        RecyclerView recyclerView = findViewById(R.id.ingredients_list);
        recyclerView.setAdapter(new IngredientsAdapter(this, mRecipe.getIngredients()));
    }
}
