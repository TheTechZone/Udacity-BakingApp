package com.example.adrian.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

        mRecipe = Parcels.unwrap(intent.getParcelableExtra("ing"));
//        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
//        if(mRecipe != null)
//        ingredientsTextView.setText(mRecipe.ingredientsList());

        RecyclerView recyclerView = findViewById(R.id.ingredients_list);
        recyclerView.setAdapter(new IngredientsAdapter(this, mRecipe.getIngredients()));
    }
}
