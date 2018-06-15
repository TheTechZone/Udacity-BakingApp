package com.example.adrian.bakingapp.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.adapter.RecipeStepsAdapter;
import com.example.adrian.bakingapp.data.model.Recipe;
import com.example.adrian.bakingapp.widget.WidgetUpdateService;

import org.parceler.Parcels;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mRecipe;
    static String RECIPE_BUNDLE_KEY = "recipeBundleExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        final Intent intent = getIntent();
        Recipe recipe = Parcels.unwrap(intent.getParcelableExtra(RECIPE_BUNDLE_KEY));
        mRecipe = recipe;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipe.getName() + " Recipe");

        final FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StepListActivity.this, IngredientsActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(StepListActivity.this,
                        fab, "listIngredients").toBundle();
                intent1.putExtra(RECIPE_BUNDLE_KEY, Parcels.wrap(mRecipe));
                startActivity(intent1, bundle);
            }
        });


        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.rv_step_list);
        setupRecyclerView((RecyclerView) recyclerView);

        WidgetUpdateService.startService(this, mRecipe.getIngredients());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecipeStepsAdapter(this, this, mRecipe.getSteps(), mTwoPane, new RecipeStepsAdapter.StepItemListener() {
            @Override
            public void onStepClick(long id) {
                Toast.makeText(StepListActivity.this, "ID: " + id, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
